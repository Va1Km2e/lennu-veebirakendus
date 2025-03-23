package com.mvaikm.lennu_veebirakendus.flight;

import com.mvaikm.lennu_veebirakendus.pageresponse.PageResponse;
import com.mvaikm.lennu_veebirakendus.seat.SeatDTO;
import com.mvaikm.lennu_veebirakendus.seat.SeatService;
import com.mvaikm.lennu_veebirakendus.seatclass.SeatClassRepository;
import com.mvaikm.lennu_veebirakendus.seatclass.SeatClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final SeatService seatService;
    private final FlightMapper flightMapper;

    public void addFlight(FlightDTO flightDTO) {
        if (flightDTO == null) {
            throw new RuntimeException("FlightDTO is null");
        }

        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setFlightNumber(flightDTO.getFlightNumber());
        flightEntity.setDepartureCity(flightDTO.getDepartureCity());
        flightEntity.setDestinationCity(flightDTO.getDestinationCity());
        flightEntity.setDepartureTime(flightDTO.getDepartureTime());
        flightEntity.setArrivalTime(flightDTO.getArrivalTime());

        Duration duration = Duration.between(flightDTO.getDepartureTime(), flightDTO.getArrivalTime());
        flightEntity.setDurationInMin(BigDecimal.valueOf(duration.toMinutes()));

        flightEntity.setBasePrice(flightDTO.getBasePrice());

        flightRepository.save(flightEntity);

        for (int i = 0; i < flightDTO.getAvailableSeats(); i++) {
            Long classId;
            if (i < 12) {
                classId = 1L;
            } else if (i < 24) {
                classId = 2L;
            } else {
                classId = 3L;
            }

            String seatNumber = generateSeatNumber(i);

            SeatDTO seatDTO = new SeatDTO();
            seatDTO.setFlightId(flightEntity.getId());
            seatDTO.setSeatNumber(seatNumber);
            seatDTO.setClassId(classId);

            seatService.addSeat(seatDTO);
        }
    }

    public void deleteFlightById(Long id) {
        flightRepository.deleteById(id);
    }

    private String generateSeatNumber(int index) {
        char row = (char) ('A' + (index / 6)); // Change row every 6 seats
        int seat = (index % 6) + 1; // Cycle seat numbers from 1 to 6

        return String.format("%c%d", row, seat);
    }

    public PageResponse<FlightDTO> findFlights(FlightSearchCriteria criteria) {
        // Build the specification or query logic
        Specification<FlightEntity> spec = Specification.where(null);
        if (criteria.minPrice() != null || criteria.maxPrice() != null) {
            spec = spec.and(FlightSpecifications.priceRange(criteria.minPrice(), criteria.maxPrice()));
        }
        if (criteria.destinationCity() != null) {
            spec = spec.and(FlightSpecifications.destinationCity(criteria.destinationCity()));
        }
        if (criteria.startDate() != null && criteria.endDate() != null) {
            spec = spec.and(FlightSpecifications.dateRange(criteria.startDate(), criteria.endDate()));
        }

        // Default pagination and sorting parameters
        int pageNumber = criteria.page() != null ? criteria.page() : 0;
        int pageSize = criteria.size() != null ? criteria.size() : 10;
        String sortBy = criteria.sortBy() != null ? criteria.sortBy() : "departureTime";
        String sortDirection = criteria.sortDirection() != null ? criteria.sortDirection() : "ASC";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(sortDirection), sortBy));

        // Fetch flights using the specification
        Page<FlightEntity> flightPage = flightRepository.findAll(spec, pageable);

        // Manually map the FlightEntity to FlightDTO
        List<FlightDTO> flightDTOs = flightPage.getContent().stream().map(flight -> {
            FlightDTO dto = new FlightDTO();
            dto.setId(flight.getId());
            dto.setFlightNumber(flight.getFlightNumber());
            dto.setDepartureCity(flight.getDepartureCity());
            dto.setDestinationCity(flight.getDestinationCity());
            dto.setDepartureTime(flight.getDepartureTime());
            dto.setArrivalTime(flight.getArrivalTime());
            dto.setDurationInMin(flight.getDurationInMin());
            dto.setBasePrice(flight.getBasePrice());
            dto.setAvailableSeats(flight.getAvailableSeats());
            return dto;
        }).toList();

        // Return the mapped DTOs in a PageResponse format
        return PageResponse.of(flightPage.map(flight -> {
            FlightDTO dto = new FlightDTO();
            dto.setId(flight.getId());
            dto.setFlightNumber(flight.getFlightNumber());
            dto.setDepartureCity(flight.getDepartureCity());
            dto.setDestinationCity(flight.getDestinationCity());
            dto.setDepartureTime(flight.getDepartureTime());
            dto.setArrivalTime(flight.getArrivalTime());
            dto.setDurationInMin(flight.getDurationInMin());
            dto.setBasePrice(flight.getBasePrice());
            dto.setAvailableSeats(flight.getAvailableSeats());
            return dto;
        }));
    }
}

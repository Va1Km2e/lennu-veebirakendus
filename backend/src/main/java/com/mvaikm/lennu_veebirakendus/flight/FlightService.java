package com.mvaikm.lennu_veebirakendus.flight;

import com.mvaikm.lennu_veebirakendus.pageresponse.PageResponse;
import com.mvaikm.lennu_veebirakendus.seat.SeatDTO;
import com.mvaikm.lennu_veebirakendus.seat.SeatService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final SeatService seatService;

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
        char row = (char) ('A' + (index / 6));
        int seat = (index % 6) + 1;

        return String.format("%c%d", row, seat);
    }

    public PageResponse<FlightDTO> findFlights(FlightSearchCriteria criteria) {
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

        int pageNumber = criteria.page() != null ? criteria.page() : 0;
        int pageSize = criteria.size() != null ? criteria.size() : 10;
        String sortBy = criteria.sortBy() != null ? criteria.sortBy() : "departureTime";
        String sortDirection = criteria.sortDirection() != null ? criteria.sortDirection() : "ASC";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(sortDirection), sortBy));

        Page<FlightEntity> flightPage = flightRepository.findAll(spec, pageable);
        
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

    public FlightDTO getFlightById(Long id) {
        Optional<FlightEntity> flightOpt = flightRepository.findById(id);

        FlightEntity flight = flightOpt.orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

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
    }
}

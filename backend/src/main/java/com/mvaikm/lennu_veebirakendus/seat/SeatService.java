package com.mvaikm.lennu_veebirakendus.seat;

import com.mvaikm.lennu_veebirakendus.flight.FlightRepository;
import com.mvaikm.lennu_veebirakendus.pageresponse.PageResponse;
import com.mvaikm.lennu_veebirakendus.seatclass.SeatClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final SeatClassRepository seatClassRepository;
    private final SeatMapper seatMapper;

    public void addSeat(SeatDTO seatDTO) {
        SeatEntity seatEntity = seatMapper.dtoToEntity(seatDTO);
        seatEntity.setFlight(flightRepository.findById(seatDTO.getFlightId()).orElseThrow(
                () -> new RuntimeException("Flight not found")));
        seatEntity.setSeatClass(seatClassRepository.findById(seatDTO.getClassId()).orElseThrow(
                () -> new RuntimeException("Seat class not found")));
        seatEntity.setSeatNumber(seatDTO.getSeatNumber());

        seatEntity.setPrice(seatEntity.getFlight().getBasePrice().multiply(seatEntity.getSeatClass().getPriceModifier()));
        seatEntity.getFlight().setAvailableSeats(seatEntity.getFlight().getAvailableSeats() + 1);
        seatRepository.save(seatEntity);
    }

    public void deleteSeat(Long id) {
        SeatEntity seat = seatRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Seat not found"));

        seat.getFlight().setAvailableSeats(seat.getFlight().getAvailableSeats() - 1);
        seatRepository.deleteById(id);

    }

    public PageResponse<SeatDTO> findSeats(SeatSearchCriteria criteria) {
        Specification<SeatEntity> spec = Specification.where(null);

        if (criteria.flightId() != null) {
            spec = spec.and(SeatSpecifications.flightId(criteria.flightId()));
        }
        if (criteria.seatClassId() != null) {
            spec = spec.and(SeatSpecifications.seatClassId(criteria.seatClassId()));
        }
        if (criteria.isOccupied() != null) {
            spec = spec.and(SeatSpecifications.isOccupied(criteria.isOccupied()));
        }

        int pageNumber = criteria.page() != null ? criteria.page() : 0;
        int pageSize = criteria.size() != null ? criteria.size() : 10;
        String sortBy = criteria.sortBy() != null ? criteria.sortBy() : "price";  // Default sort by price
        String sortDirection = criteria.sortDirection() != null ? criteria.sortDirection() : "ASC";

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(sortDirection), sortBy));

        Page<SeatEntity> seatPage = seatRepository.findAll(spec, pageable);

        return PageResponse.of(seatPage.map(seat -> {
            SeatDTO dto = new SeatDTO();
            dto.setId(seat.getId());
            dto.setSeatNumber(seat.getSeatNumber());
            dto.setFlightId(seat.getFlight().getId());
            dto.setClassId(seat.getSeatClass().getId());
            dto.setOccupied(seat.isOccupied());
            dto.setPrice(seat.getPrice());
            return dto;
        }));
    }

    public SeatDTO getSeatById(Long id) {
        Optional<SeatEntity> seatEntityOptional = seatRepository.findById(id);

        SeatEntity seatEntity = seatEntityOptional.orElseThrow(() -> new RuntimeException("seat not found with id: " + id));

        SeatDTO dto = new SeatDTO();
        dto.setId(seatEntity.getId());
        dto.setSeatNumber(seatEntity.getSeatNumber());
        dto.setFlightId(seatEntity.getFlight().getId());
        dto.setClassId(seatEntity.getSeatClass().getId());
        dto.setOccupied(seatEntity.isOccupied());
        dto.setPrice(seatEntity.getPrice());

        return dto;
    }
}

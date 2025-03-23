package com.mvaikm.lennu_veebirakendus.seat;

import com.mvaikm.lennu_veebirakendus.flight.FlightRepository;
import com.mvaikm.lennu_veebirakendus.seatclass.SeatClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void addSeat(Long id, String seatNumber, int classId) {

    }
}

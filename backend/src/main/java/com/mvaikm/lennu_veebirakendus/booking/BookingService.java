package com.mvaikm.lennu_veebirakendus.booking;

import com.mvaikm.lennu_veebirakendus.flight.FlightEntity;
import com.mvaikm.lennu_veebirakendus.flight.FlightRepository;
import com.mvaikm.lennu_veebirakendus.seat.SeatEntity;
import com.mvaikm.lennu_veebirakendus.seat.SeatRepository;
import com.mvaikm.lennu_veebirakendus.user.UserEntity;
import com.mvaikm.lennu_veebirakendus.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    @Transactional
    public void createBooking(BookingDTO bookingDTO) {
        BookingEntity bookingEntity = new BookingEntity();
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<SeatEntity> seats = new ArrayList<>();

        bookingEntity.setBookingDate(bookingDTO.getBookingDate());

        //Retrieve UserEntity by userId
        UserEntity user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + bookingDTO.getUserId()));
        bookingEntity.setUser(user);

        //Retrieve FlightEntity by flightId
        FlightEntity flight = flightRepository.findById(bookingDTO.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + bookingDTO.getFlightId()));
        bookingEntity.setFlight(flight);

        //associate seats with booking
        for (Long seatId : bookingDTO.getSeatIds()) {
            SeatEntity seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found with id: " + seatId));

            if (seat.getBooking() != null) {
                throw new RuntimeException("Seat with ID " + seatId + " is already booked!");
            }

            seat.setBooking(bookingEntity);
            seat.setOccupied(true);
            seat.getFlight().setAvailableSeats(seat.getFlight().getAvailableSeats() - 1);
            seats.add(seat);

            totalPrice = totalPrice.add(seat.getPrice());
        }

        bookingEntity.setSeats(seats);
        bookingEntity.setTotalPrice(totalPrice);
        bookingEntity.setStatus(BookingStatus.valueOf("COMPLETED"));


        bookingRepository.save(bookingEntity);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
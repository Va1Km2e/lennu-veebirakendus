package com.mvaikm.lennu_veebirakendus.booking;

import com.mvaikm.lennu_veebirakendus.flight.FlightEntity;
import com.mvaikm.lennu_veebirakendus.flight.FlightRepository;
import com.mvaikm.lennu_veebirakendus.seat.SeatEntity;
import com.mvaikm.lennu_veebirakendus.seat.SeatRepository;
import com.mvaikm.lennu_veebirakendus.user.UserEntity;
import com.mvaikm.lennu_veebirakendus.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.internal.model.common.FinalField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public void createBooking(BookingDTO bookingDTO) {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(bookingDTO.getId());
        bookingEntity.setStatus(bookingDTO.getStatus());
        bookingEntity.setTotalPrice(bookingDTO.getTotalPrice());

        System.out.println(bookingEntity.getStatus() + bookingEntity.getId() + bookingEntity.getTotalPrice());
        // Save or update the entity (depends on your use case)
        bookingRepository.save(bookingEntity);  // Alternatively, saveOrUpdate
    }

}

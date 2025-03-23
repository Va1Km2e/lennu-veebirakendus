package com.mvaikm.lennu_veebirakendus.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long flightId;
    private List<Long> seatIds;
    private String status;
    private LocalDateTime bookingDate;
    private BigDecimal totalPrice;
}

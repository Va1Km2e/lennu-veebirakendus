package com.mvaikm.lennu_veebirakendus.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    private Long Id;
    private Long userId;
    private Long flightId;
    private List<Long> seatIds;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime creationDate;
}

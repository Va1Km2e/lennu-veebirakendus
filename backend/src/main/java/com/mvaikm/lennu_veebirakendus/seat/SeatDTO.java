package com.mvaikm.lennu_veebirakendus.seat;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeatDTO {
    private Long id;
    private Long flightId;
    private String seatNumber;
    private Long classId;
    private boolean isOccupied;
    private BigDecimal price;
}

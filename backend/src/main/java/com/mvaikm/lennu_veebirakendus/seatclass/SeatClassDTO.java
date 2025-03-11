package com.mvaikm.lennu_veebirakendus.seatclass;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeatClassDTO {
    private int id;
    private String className;
    private String description;
    private BigDecimal priceModifier;
}

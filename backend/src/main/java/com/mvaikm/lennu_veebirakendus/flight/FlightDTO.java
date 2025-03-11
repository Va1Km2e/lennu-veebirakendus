package com.mvaikm.lennu_veebirakendus.flight;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FlightDTO {
    private Long id;
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal durationInMin;
    private BigDecimal basePrice;
    private int availableSeats;
}

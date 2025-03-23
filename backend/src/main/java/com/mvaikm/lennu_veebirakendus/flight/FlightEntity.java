package com.mvaikm.lennu_veebirakendus.flight;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flights")
@Entity
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_generator")
    @SequenceGenerator(name="flight_generator", sequenceName = "flights_seq", allocationSize=1)
    private Long id;

    @Column(name = "flight_number", unique = true)
    private String flightNumber;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "destination_city")
    private String destinationCity;

    @Column(name = "departure_time")
    private LocalDateTime  departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "duration_in_min")
    private BigDecimal durationInMin;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "available_seats")
    private int availableSeats;
}

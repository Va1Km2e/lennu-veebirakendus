package com.mvaikm.lennu_veebirakendus.seat;

import com.mvaikm.lennu_veebirakendus.flight.FlightEntity;
import com.mvaikm.lennu_veebirakendus.seatclass.SeatClassEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seats")
@Entity
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_generator")
    @SequenceGenerator(name="seat_generator", sequenceName = "seat_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private SeatClassEntity seatClass;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}

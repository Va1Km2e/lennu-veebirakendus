package com.mvaikm.lennu_veebirakendus.booking;

import com.mvaikm.lennu_veebirakendus.flight.FlightEntity;
import com.mvaikm.lennu_veebirakendus.seat.SeatEntity;
import com.mvaikm.lennu_veebirakendus.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
@Entity
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_generator")
    @SequenceGenerator(name="booking_generator", sequenceName = "booking_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<SeatEntity> seats;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}

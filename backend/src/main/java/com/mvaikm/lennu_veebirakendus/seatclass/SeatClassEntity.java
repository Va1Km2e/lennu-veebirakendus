package com.mvaikm.lennu_veebirakendus.seatclass;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seatClasses")
@Entity
public class SeatClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seatClass_generator")
    @SequenceGenerator(name="seatClass_generator", sequenceName = "seatClass_seq", allocationSize=1)
    private Long id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "description")
    private String description;

    @Column(name = "price_modifier")
    private BigDecimal priceModifier;
}

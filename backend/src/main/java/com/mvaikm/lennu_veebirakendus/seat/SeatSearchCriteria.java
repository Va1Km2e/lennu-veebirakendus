package com.mvaikm.lennu_veebirakendus.seat;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SeatSearchCriteria(
        Long flightId,
        BigDecimal price,
        Long seatClassId,
        Boolean isOccupied,
        @Pattern(regexp = "price|flightId", message = "Sort by must be either 'price' or 'flightId'")
        String sortBy,
        @Pattern(regexp = "ASC|DESC", message = "Sort direction must be either 'ASC' or 'DESC'")
        String sortDirection,
        Integer page,
        Integer size
) {}

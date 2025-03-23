package com.mvaikm.lennu_veebirakendus.flight;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record FlightSearchCriteria(
        String departureCity,
        String destinationCity,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        @Min(0) Integer page,
        @Min(1) @Max(100) Integer size,
        @Pattern(regexp = "departureTime|flightNumber")
        String sortBy,
        @Pattern(regexp = "ASC|DESC")
        String sortDirection
) {}

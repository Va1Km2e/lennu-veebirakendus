package com.mvaikm.lennu_veebirakendus.flight;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightSpecifications {

    private FlightSpecifications() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<FlightEntity> destinationCity(String destinationCity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("destinationCity"), destinationCity);
    }

    public static Specification<FlightEntity> dateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("departureTime"), startDate, endDate);
    }

    public static Specification<FlightEntity> priceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("basePrice"), minPrice, maxPrice);
    }
}

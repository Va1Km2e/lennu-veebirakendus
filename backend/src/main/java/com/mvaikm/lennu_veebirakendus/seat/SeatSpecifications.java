package com.mvaikm.lennu_veebirakendus.seat;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;
import java.math.BigDecimal;

public class SeatSpecifications {

    private SeatSpecifications() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<SeatEntity> flightId(Long flightId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("flight").get("id"), flightId);
    }

    public static Specification<SeatEntity> seatClassId(Long seatClassId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("seatClass").get("id"), seatClassId);
    }

    public static Specification<SeatEntity> isOccupied(Boolean isOccupied) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isOccupied"), isOccupied);
    }

    public static Specification<SeatEntity> seatNumber(String seatNumber) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("seatNumber"), seatNumber);
    }

    public static Specification<SeatEntity> sortBy(String sortBy, String sortDirection) {
        return (root, query, criteriaBuilder) -> {
            if (sortBy == null || sortDirection == null) {
                return criteriaBuilder.conjunction();
            }

            if ("price".equalsIgnoreCase(sortBy)) {
                if ("ASC".equalsIgnoreCase(sortDirection)) {
                    query.orderBy(criteriaBuilder.asc(root.get("price")));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get("price")));
                }
            } else if ("flightId".equalsIgnoreCase(sortBy)) {
                if ("ASC".equalsIgnoreCase(sortDirection)) {
                    query.orderBy(criteriaBuilder.asc(root.get("flight").get("id")));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get("flight").get("id")));
                }
            }
            return criteriaBuilder.conjunction();
        };
    }
}

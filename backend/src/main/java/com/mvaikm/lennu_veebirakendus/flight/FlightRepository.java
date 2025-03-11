package com.mvaikm.lennu_veebirakendus.flight;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
}
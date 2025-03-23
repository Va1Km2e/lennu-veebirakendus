package com.mvaikm.lennu_veebirakendus.seatclass;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SeatClassRepository extends JpaRepository<SeatClassEntity, Long> {
    Long id(Long id);

    boolean existsByClassName(String className);
}
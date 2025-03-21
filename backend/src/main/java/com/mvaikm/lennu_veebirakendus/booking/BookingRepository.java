package com.mvaikm.lennu_veebirakendus.booking;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
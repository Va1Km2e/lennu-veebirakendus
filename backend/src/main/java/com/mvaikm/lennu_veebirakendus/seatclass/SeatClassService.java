package com.mvaikm.lennu_veebirakendus.seatclass;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatClassService {
    private final SeatClassRepository seatClassRepository;

    public void deleteSeatClassById(Long seatClassId) {
        seatClassRepository.deleteById(seatClassId);
    }


    public void addSeatClass(SeatClassDTO seatClassDTO) {
        if (seatClassDTO == null) {
            throw new RuntimeException("FlightDTO is null");
        }

        SeatClassEntity seatClassEntity = new SeatClassEntity();
        seatClassEntity.setClassName(seatClassDTO.getClassName());
        seatClassEntity.setDescription(seatClassDTO.getDescription());
        seatClassEntity.setPriceModifier(seatClassDTO.getPriceModifier());

        seatClassRepository.save(seatClassEntity);
    }
}

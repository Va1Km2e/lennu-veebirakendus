package com.mvaikm.lennu_veebirakendus.seat;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeatMapper {

    SeatDTO toDto(SeatEntity seatEntity);

    List<SeatDTO> toDtoList(List<SeatEntity> seatEntityList);

    SeatEntity dtoToEntity(SeatDTO seatDTO);
}
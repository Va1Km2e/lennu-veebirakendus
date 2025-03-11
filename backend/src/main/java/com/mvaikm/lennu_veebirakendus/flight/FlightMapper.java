package com.mvaikm.lennu_veebirakendus.flight;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlightMapper {

    FlightDTO toDto(FlightEntity flightEntity);

    List<FlightDTO> toDtoList(List<FlightEntity> flightEntities);

    FlightEntity dtoToEntity(FlightDTO flightDTO);
}
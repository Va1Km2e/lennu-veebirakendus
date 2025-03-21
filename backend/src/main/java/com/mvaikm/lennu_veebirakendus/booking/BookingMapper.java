package com.mvaikm.lennu_veebirakendus.booking;

import com.mvaikm.lennu_veebirakendus.flight.FlightDTO;
import com.mvaikm.lennu_veebirakendus.flight.FlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    BookingDTO toDto(BookingEntity bookingEntity);

    List<BookingDTO> toDtoList(List<BookingEntity> flightEntities);

    FlightEntity dtoToEntity(FlightDTO flightDTO);
}
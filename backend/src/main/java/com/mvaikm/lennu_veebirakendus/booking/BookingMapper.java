package com.mvaikm.lennu_veebirakendus.booking;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    BookingDTO toDto(BookingEntity bookingEntity);

    List<BookingDTO> toDtoList(List<BookingEntity> bookingEntities);

    BookingEntity dtoToEntity(BookingDTO bookingDTO);
}
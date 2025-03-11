package com.mvaikm.lennu_veebirakendus.user;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDTO toDto(UserEntity user);

    List<UserDTO> toDtoList(List<UserEntity> userEntityList);

    UserEntity dtoToEntity(UserDTO userDTO);
}
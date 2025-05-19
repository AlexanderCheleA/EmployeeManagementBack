package com.hiberius.hiberius.mapper.request;

import com.hiberius.hiberius.dto.request.DepartmentDTORq;
import com.hiberius.hiberius.models.DepartmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentRequestMapper {

    DepartmentRequestMapper INSTANCE = Mappers.getMapper(DepartmentRequestMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "employees", ignore = true)
    DepartmentModel toEntity(DepartmentDTORq dto);

}

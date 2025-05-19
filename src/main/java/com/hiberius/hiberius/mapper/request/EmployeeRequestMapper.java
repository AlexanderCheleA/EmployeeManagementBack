package com.hiberius.hiberius.mapper.request;

import com.hiberius.hiberius.dto.request.EmployeeDTORq;
import com.hiberius.hiberius.models.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = DepartmentRequestMapper.class)
public interface EmployeeRequestMapper {

    EmployeeRequestMapper INSTANCE = Mappers.getMapper(EmployeeRequestMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "salary", source = "salary")
    @Mapping(target = "initDate", source = "initDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "department", ignore = true)
    EmployeeModel toEntity(EmployeeDTORq dto);


    List<EmployeeModel> toEntities(List<EmployeeDTORq> dtoList);

}

package com.hiberius.hiberius.mapper.response;

import com.hiberius.hiberius.dto.response.EmployeeDTORs;
import com.hiberius.hiberius.models.EmployeeModel;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = "spring", uses = { DepartmentResponseMapper.class })
public interface EmployeeResponseMapper {

    EmployeeResponseMapper INSTANCE = Mappers.getMapper(EmployeeResponseMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "salary", source = "salary")
    @Mapping(target = "initDate", source = "initDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "departmentId", source = "department.id")
    EmployeeDTORs toDTO(EmployeeModel entity);

    default EmployeeDTORs toDTODepartamentId(EmployeeModel entity, Long departmentId) {
        EmployeeDTORs dto = toDTO(entity);
        dto.setDepartmentId(departmentId);
        return dto;
    }

    default List<EmployeeDTORs> toDTOs(List<EmployeeModel> entities, Long departamentId) {
        return entities.stream().map(entity -> toDTODepartamentId(entity, departamentId)).toList();
    }

    @AfterMapping
    default void afterMapping(EmployeeModel entity, @MappingTarget EmployeeDTORs dto) {
        dto.setStatus(entity.getStatus().getDescripcion());
    }
}

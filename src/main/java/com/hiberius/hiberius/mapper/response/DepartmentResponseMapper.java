package com.hiberius.hiberius.mapper.response;

import com.hiberius.hiberius.dto.response.DepartmentDTORs;
import com.hiberius.hiberius.dto.response.EmployeeDTORs;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.models.EmployeeModel;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentResponseMapper {

    DepartmentResponseMapper INSTANCE = Mappers.getMapper(DepartmentResponseMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", ignore = true)
    DepartmentDTORs toDTO(DepartmentModel entity);

    default List<DepartmentDTORs> toDTOs(List<DepartmentModel> entities) {
        return entities.stream().map(entity -> {
            DepartmentDTORs departmentDTO = toDTO(entity);
            List<EmployeeModel> employees = entity.getEmployees();
            if (employees != null && !employees.isEmpty()) {
                List<EmployeeDTORs> employeeDTOList = EmployeeResponseMapper.INSTANCE.toDTOs(employees, departmentDTO.getId());
                departmentDTO.setEmployees(employeeDTOList);
            }
            return toDTO(entity);
        }).toList();
    }

    @AfterMapping
    default void afterMapping(DepartmentModel entity, @MappingTarget DepartmentDTORs dto) {
        dto.setStatus(entity.getStatus().getDescripcion());
    }
}

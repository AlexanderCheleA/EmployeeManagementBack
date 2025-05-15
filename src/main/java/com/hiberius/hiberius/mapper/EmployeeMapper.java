package com.hiberius.hiberius.mapper;

import com.hiberius.hiberius.dto.EmployeeDTO;
import com.hiberius.hiberius.models.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "salary", source = "salary")
    @Mapping(target = "initDate", source = "initDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "employeeStatus", source = "employeeStatus")
    EmployeeModel toEntity(EmployeeDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "salary", source = "salary")
    @Mapping(target = "initDate", source = "initDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "employeeStatus", source = "employeeStatus")
    @Mapping(target = "departmentId", source = "department.id")
    EmployeeDTO toDTO(EmployeeModel entity);

    default EmployeeDTO toDTODepartamentId(EmployeeModel entity, Long departmentId) {
        EmployeeDTO dto = toDTO(entity);
        dto.setDepartmentId(departmentId);
        return dto;
    }

    List<EmployeeModel> toEntities(List<EmployeeDTO> dtoList);

    default List<EmployeeDTO> toDTOs(List<EmployeeModel> entities, Long departamentId) {
        return entities.stream().map(entity -> toDTODepartamentId(entity, departamentId)).toList();
    }

}

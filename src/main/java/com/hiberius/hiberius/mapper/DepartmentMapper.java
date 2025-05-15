package com.hiberius.hiberius.mapper;

import com.hiberius.hiberius.dto.DepartmentDTO;
import com.hiberius.hiberius.dto.EmployeeDTO;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.models.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    DepartmentModel toEntity(DepartmentDTO dto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    DepartmentDTO toDTO(DepartmentModel entity);

    default List<DepartmentDTO> toDTOs(List<DepartmentModel> entities) {
        return entities.stream().map(entity -> {
            DepartmentDTO departmentDTO = toDTO(entity);
            List<EmployeeModel> employees = entity.getEmployees();
            if (employees != null && !employees.isEmpty()) {
                List<EmployeeDTO> employeeDTOList = EmployeeMapper.INSTANCE.toDTOs(employees, departmentDTO.getId());
                departmentDTO.setEmployees(employeeDTOList);
            }
            return toDTO(entity);
        }).toList();
    }

}

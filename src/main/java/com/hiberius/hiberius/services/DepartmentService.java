package com.hiberius.hiberius.services;

import com.hiberius.hiberius.dto.base.GlobalStatus;
import com.hiberius.hiberius.dto.request.DepartmentDTORq;
import com.hiberius.hiberius.dto.request.EmployeeDTORq;
import com.hiberius.hiberius.dto.response.DepartmentDTORs;
import com.hiberius.hiberius.mapper.request.DepartmentRequestMapper;
import com.hiberius.hiberius.mapper.response.DepartmentResponseMapper;
import com.hiberius.hiberius.mapper.request.EmployeeRequestMapper;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.models.EmployeeModel;
import com.hiberius.hiberius.repository.DepartmentRepository;
import com.hiberius.hiberius.utils.MessagesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentDTORs createDepartment(DepartmentDTORq dto) {
        DepartmentModel department = DepartmentRequestMapper.INSTANCE.toEntity(dto);

        List<DepartmentModel> existing = departmentRepository.findByNameAndStatus(department.getName(), department.getStatus());
        if (!existing.isEmpty()) {
            throw new RuntimeException(
                    String.format(MessagesUtil.Department.ERROR_EXISTS, department.getName())
            );
        }


        List<EmployeeDTORq> employees = dto.getEmployees();
        if (employees != null && !employees.isEmpty()) {
            List<EmployeeModel> employeeModelList = EmployeeRequestMapper.INSTANCE.toEntities(employees);

            for (EmployeeModel employee : employeeModelList) {
                employee.setDepartment(department);
            }

            department.setEmployees(employeeModelList);
        }

        DepartmentModel saved = departmentRepository.save(department);
        return DepartmentResponseMapper.INSTANCE.toDTO(saved);
    }

    public DepartmentDTORs logicalDeleteDepartment(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException(MessagesUtil.Department.INVALID_ID);
        }

        DepartmentModel departmentModel = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                            String.format(MessagesUtil.Department.NOT_FOUND, id)
                        )
                );

        if (departmentModel.getStatus() == GlobalStatus.I) {
            throw new RuntimeException(
                    String.format(MessagesUtil.Department.ALREADY_INACTIVE, id)
            );
        }

        departmentModel.setStatus(GlobalStatus.I);
        DepartmentModel updated = departmentRepository.save(departmentModel);
        return DepartmentResponseMapper.INSTANCE.toDTO(updated);
    }

    public List<DepartmentDTORs> getDepartment() {
        List<DepartmentModel> departments = departmentRepository.findAll();

        return departments.stream()
                .filter(dept -> dept.getStatus() == GlobalStatus.A)
                .map(dept -> {
                    DepartmentDTORs dto = new DepartmentDTORs();
                    dto.setId(dept.getId());
                    dto.setName(dept.getName());
                    dto.setStatus(dept.getStatus().getDescripcion());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<DepartmentDTORs> getDepartmentEmployee() {
        List<DepartmentModel> departments = departmentRepository.findAll();
        List<DepartmentDTORs> departmentDTOs = DepartmentResponseMapper.INSTANCE.toDTOs(departments);
        return departmentDTOs;
    }

}

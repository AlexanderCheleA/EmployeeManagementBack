package com.hiberius.hiberius.services;


import com.hiberius.hiberius.dto.base.GlobalStatus;
import com.hiberius.hiberius.dto.request.EmployeeDTORq;
import com.hiberius.hiberius.dto.response.EmployeeDTORs;
import com.hiberius.hiberius.mapper.request.EmployeeRequestMapper;
import com.hiberius.hiberius.mapper.response.EmployeeResponseMapper;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.models.EmployeeModel;
import com.hiberius.hiberius.repository.DepartmentRepository;
import com.hiberius.hiberius.repository.EmployeeRepository;
import com.hiberius.hiberius.utils.MessagesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeDTORs createEmployee(EmployeeDTORq dto, Long departmentId) {
        DepartmentModel department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException(
                        String.format(MessagesUtil.Department.NOT_FOUND, departmentId)));

        if (dto.getStatus() == null) {
            dto.setStatus(GlobalStatus.A);
        }

        dto.setDepartmentId(department.getId());
        EmployeeModel employee = EmployeeRequestMapper.INSTANCE.toEntity(dto);
        employee.setDepartment(department);
        EmployeeModel saved = employeeRepository.save(employee);
        return EmployeeResponseMapper.INSTANCE.toDTO(saved);
    }

    public EmployeeDTORs logicalDeleteEmployee(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException(MessagesUtil.Employee.INVALID_ID);
        }

        EmployeeModel employeeModel = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format(MessagesUtil.Department.NOT_FOUND, id)
                )
            );

        if (employeeModel.getStatus() == GlobalStatus.I) {
            throw new RuntimeException(
                    String.format(MessagesUtil.Employee.ALREADY_INACTIVE, id)
            );
        }

        employeeModel.setStatus(GlobalStatus.I);
        EmployeeModel updated = employeeRepository.save(employeeModel);
        return EmployeeResponseMapper.INSTANCE.toDTO(updated);
    }

    public EmployeeDTORs getEmployeeWithHighestSalary() {
        return employeeRepository.findTopByStatusOrderBySalaryDesc(GlobalStatus.A)
                .map(EmployeeResponseMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new RuntimeException(MessagesUtil.Employee.ERROR_FUNDS));
    }

    public EmployeeDTORs getYoungestEmployee() {
        return employeeRepository.findTopByStatusOrderByAgeAsc(GlobalStatus.A)
                .map(EmployeeResponseMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new RuntimeException(MessagesUtil.Employee.ERROR_FUNDS));
    }

    public Long countEmployeesJoinedLastMonth() {
        LocalDate now = LocalDate.now();
        LocalDate startOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate endOfLastMonth = startOfLastMonth.withDayOfMonth(startOfLastMonth.lengthOfMonth());

        return employeeRepository.countByInitDateBetween(startOfLastMonth, endOfLastMonth);
    }

    public List<EmployeeDTORs> getEmployee() {
        List<EmployeeModel> employees = employeeRepository.findAll();
        List<EmployeeDTORs> employeeDTOS = new ArrayList<>();

        if (employees != null && !employees.isEmpty()) {
            for (EmployeeModel employee : employees) {
                EmployeeDTORs employeeDTORs = EmployeeResponseMapper.INSTANCE.toDTO(employee);
                employeeDTOS.add(employeeDTORs);
            }
        }
        return employeeDTOS;
    }

}

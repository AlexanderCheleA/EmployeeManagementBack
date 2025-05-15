package com.hiberius.hiberius.services;

import com.hiberius.hiberius.dto.DepartmentDTO;
import com.hiberius.hiberius.dto.EmployeeDTO;
import com.hiberius.hiberius.mapper.DepartmentMapper;
import com.hiberius.hiberius.mapper.EmployeeMapper;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.models.EmployeeModel;
import com.hiberius.hiberius.repository.DepartmentRepository;
import com.hiberius.hiberius.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private static final String MSG_ERROR_FUNDS = "No se encontro empleados activos";
    public EmployeeDTO createEmployee(EmployeeDTO dto, Long departmentId) {
        Optional<DepartmentModel> dept = departmentRepository.findById(departmentId);

        if (dept.isEmpty()) {
            throw new RuntimeException("Department not found with ID: " + departmentId);
        }

        if (dto.getEmployeeStatus() == null || dto.getEmployeeStatus().trim().isEmpty()) {
            dto.setEmployeeStatus("A");
        }

        DepartmentDTO departmentDTO = DepartmentMapper.INSTANCE.toDTO(dept.get());

        dto.setDepartmentId(departmentDTO.getId());
        EmployeeModel employee = EmployeeMapper.INSTANCE.toEntity(dto);
        employee.setDepartment(dept.get());
        EmployeeModel saved = employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.toDTO(saved);
    }

    public void logicalDeleteEmployee(Long id) {
        employeeRepository.findById(id).ifPresent(emp -> {
            emp.setEmployeeStatus("I");
            employeeRepository.save(emp);
        });
    }

    public EmployeeDTO getEmployeeWithHighestSalary() {
        return employeeRepository.findTopByEmployeeStatusOrderBySalaryDesc("A")
                .map(EmployeeMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new RuntimeException(MSG_ERROR_FUNDS));
    }

    public EmployeeDTO getYoungestEmployee() {
        return employeeRepository.findTopByEmployeeStatusOrderByAgeAsc("A")
                .map(EmployeeMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new RuntimeException(MSG_ERROR_FUNDS));
    }

    public Long countEmployeesJoinedLastMonth() {
        LocalDate now = LocalDate.now();
        LocalDate oneMonthAgo = now.minusMonths(1);
        return employeeRepository.countByInitDateBetween(oneMonthAgo, now);
    }

    public List<EmployeeDTO> getEmployee() {
        List<EmployeeModel> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = EmployeeMapper.INSTANCE.toDTOs(employees, null);
        return employeeDTOS;
    }

}

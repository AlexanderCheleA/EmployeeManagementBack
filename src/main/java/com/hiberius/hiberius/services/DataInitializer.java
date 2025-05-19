package com.hiberius.hiberius.services;

import com.hiberius.hiberius.dto.base.GlobalStatus;
import com.hiberius.hiberius.dto.request.DepartmentDTORq;
import com.hiberius.hiberius.mapper.request.DepartmentRequestMapper;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.models.EmployeeModel;
import com.hiberius.hiberius.repository.DepartmentRepository;
import com.hiberius.hiberius.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            employeeRepository.deleteAll();
            departmentRepository.deleteAll();

            List<DepartmentDTORq> departmentDTOs = List.of(
                    createDepartment("Sistemas", GlobalStatus.A),
                    createDepartment("Contabilidad", GlobalStatus.A),
                    createDepartment("RRHH", GlobalStatus.I),
                    createDepartment("People", GlobalStatus.A)
            );

            List<DepartmentModel> departments = departmentDTOs.stream()
                    .map(DepartmentRequestMapper.INSTANCE::toEntity)
                    .collect(Collectors.toList());

            List<DepartmentModel> savedDepartments = departmentRepository.saveAll(departments);

            DepartmentModel sistemas = savedDepartments.stream().filter(d -> d.getName().equals("Sistemas")).findFirst().orElseThrow();
            DepartmentModel contabilidad = savedDepartments.stream().filter(d -> d.getName().equals("Contabilidad")).findFirst().orElseThrow();

            List<EmployeeModel> employees = List.of(
                    createEmployee("Luis", "Pérez", 22, 500.0,
                            LocalDate.of(2021, 2, 10), null, GlobalStatus.A, sistemas),

                    createEmployee("Maria", "Gonzalez", 25, null,
                            LocalDate.of(2020, 3, 11), null, GlobalStatus.A, sistemas),

                    createEmployee("Pedro", "Gómez", 30, null,
                            LocalDate.of(2020, 3, 11), LocalDate.of(2024, 5, 20), GlobalStatus.I, contabilidad),

                    createEmployee("José", "López", 20, null,
                            null, null, GlobalStatus.A, contabilidad)
            );


            employeeRepository.saveAll(employees);
        };
    }

    private DepartmentDTORq createDepartment(String name, GlobalStatus status) {
        DepartmentDTORq dto = new DepartmentDTORq();
        dto.setName(name);
        dto.setStatus(status);
        return dto;
    }
    private EmployeeModel createEmployee(String name, String surname, int age, Double salary,
                                         LocalDate entryDate, LocalDate exitDate, GlobalStatus status,
                                         DepartmentModel department) {
        EmployeeModel employee = new EmployeeModel();
        employee.setName(name);
        employee.setLastName(surname);
        employee.setAge(age);
        employee.setSalary(salary);
        employee.setInitDate(entryDate);
        employee.setEndDate(exitDate);
        employee.setStatus(status);
        employee.setDepartment(department);
        return employee;
    }
}
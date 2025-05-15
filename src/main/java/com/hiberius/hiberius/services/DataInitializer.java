package com.hiberius.hiberius.services;

import com.hiberius.hiberius.dto.DepartmentDTO;
import com.hiberius.hiberius.mapper.DepartmentMapper;
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

            List<DepartmentDTO> departmentDTOs = List.of(
                    createDepartment("Sistemas", "A"),
                    createDepartment("Contabilidad", "A"),
                    createDepartment("RRHH", "I"),
                    createDepartment("People", "A")
            );

            List<DepartmentModel> departments = departmentDTOs.stream()
                    .map(DepartmentMapper.INSTANCE::toEntity)
                    .collect(Collectors.toList());

            /*List<DepartmentModel> savedDepartments = departmentRepository.saveAll(departments);

            DepartmentModel sistemas = savedDepartments.stream().filter(d -> d.getName().equals("Sistemas")).findFirst().orElseThrow();
            DepartmentModel contabilidad = savedDepartments.stream().filter(d -> d.getName().equals("Contabilidad")).findFirst().orElseThrow();

            List<EmployeeModel> employees = List.of(
                    createEmployee("Luis", "Pérez", 22, 500.0,
                            LocalDate.of(2021, 2, 10), null, "A", sistemas),

                    createEmployee("Maria", "Gonzalez", 25, null,
                            LocalDate.of(2020, 3, 11), null, "A", sistemas),

                    createEmployee("Pedro", "Gómez", 30, null,
                            LocalDate.of(2020, 3, 11), LocalDate.of(2024, 5, 20), "I", contabilidad),

                    createEmployee("José", "López", 20, null,
                            null, null, "A", contabilidad)
            );


            employeeRepository.saveAll(employees);*/
        };
    }

    private DepartmentDTO createDepartment(String name, String status) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName(name);
        dto.setStatus(status);
        return dto;
    }
    private EmployeeModel createEmployee(String name, String surname, int age, Double salary,
                                         LocalDate entryDate, LocalDate exitDate, String status,
                                         DepartmentModel department) {
        EmployeeModel employee = new EmployeeModel();
        employee.setName(name);
        employee.setLastName(surname);
        employee.setAge(age);
        employee.setSalary(salary);
        employee.setInitDate(entryDate);
        employee.setEndDate(exitDate);
        employee.setEmployeeStatus(status);
        employee.setDepartment(department);
        return employee;
    }
}
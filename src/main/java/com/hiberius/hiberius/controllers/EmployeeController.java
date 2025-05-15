package com.hiberius.hiberius.controllers;

import com.hiberius.hiberius.dto.DepartmentDTO;
import com.hiberius.hiberius.dto.EmployeeDTO;
import com.hiberius.hiberius.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployee() {
        return ResponseEntity.ok(employeeService.getEmployee());
    }

    @PostMapping("/create/{departmentId}")
    public ResponseEntity<EmployeeDTO> createEmployee(@PathVariable @Valid Long departmentId,
                                                      @RequestBody @Valid EmployeeDTO dto) {
        EmployeeDTO created = employeeService.createEmployee(dto, departmentId);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable @Valid Long employeeId) {
        employeeService.logicalDeleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/highestSalary")
    public ResponseEntity<EmployeeDTO> getEmployeeWithHighestSalary() {
        return ResponseEntity.ok(employeeService.getEmployeeWithHighestSalary());
    }

    @GetMapping("/lowerAge")
    public ResponseEntity<EmployeeDTO> getYoungestEmployee() {
        return ResponseEntity.ok(employeeService.getYoungestEmployee());
    }

    @GetMapping("/countLastMonth")
    public ResponseEntity<Long> countEmployeesJoinedLastMonth() {
        return ResponseEntity.ok(employeeService.countEmployeesJoinedLastMonth());
    }
}

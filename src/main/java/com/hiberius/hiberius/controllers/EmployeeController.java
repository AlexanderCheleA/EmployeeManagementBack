package com.hiberius.hiberius.controllers;

import com.hiberius.hiberius.dto.request.EmployeeDTORq;
import com.hiberius.hiberius.dto.response.EmployeeDTORs;
import com.hiberius.hiberius.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTORs>> getEmployee() {
        return ResponseEntity.ok(employeeService.getEmployee());
    }

    @PostMapping("/create/{departmentId}")
    public ResponseEntity<EmployeeDTORs> createEmployee(@PathVariable @Valid Long departmentId,
                                                      @RequestBody @Valid EmployeeDTORq dto) {
        EmployeeDTORs created = employeeService.createEmployee(dto, departmentId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{employeeId}")
    public ResponseEntity<EmployeeDTORs> deleteEmployee(@PathVariable @Valid Long employeeId) {
        EmployeeDTORs employeeDTO = employeeService.logicalDeleteEmployee(employeeId);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping("/highestSalary")
    public ResponseEntity<EmployeeDTORs> getEmployeeWithHighestSalary() {
        return ResponseEntity.ok(employeeService.getEmployeeWithHighestSalary());
    }

    @GetMapping("/lowerAge")
    public ResponseEntity<EmployeeDTORs> getYoungestEmployee() {
        return ResponseEntity.ok(employeeService.getYoungestEmployee());
    }

    @GetMapping("/countLastMonth")
    public ResponseEntity<Long> countEmployeesJoinedLastMonth() {
        return ResponseEntity.ok(employeeService.countEmployeesJoinedLastMonth());
    }
}

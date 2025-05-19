package com.hiberius.hiberius.controllers;

import com.hiberius.hiberius.dto.request.EmployeeDTORq;
import com.hiberius.hiberius.dto.response.EmployeeDTORs;
import com.hiberius.hiberius.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmployee_shouldReturnListOfEmployees() {
        List<EmployeeDTORs> mockList = List.of(new EmployeeDTORs());
        when(employeeService.getEmployee()).thenReturn(mockList);

        ResponseEntity<List<EmployeeDTORs>> response = employeeController.getEmployee();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
        verify(employeeService, times(1)).getEmployee();
    }

    @Test
    void createEmployee_shouldReturnCreatedEmployee() {
        Long departmentId = 1L;
        EmployeeDTORq dto = new EmployeeDTORq();
        EmployeeDTORs createdDto = new EmployeeDTORs();

        when(employeeService.createEmployee(dto, departmentId)).thenReturn(createdDto);

        ResponseEntity<EmployeeDTORs> response = employeeController.createEmployee(departmentId, dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
        verify(employeeService, times(1)).createEmployee(dto, departmentId);
    }

    @Test
    void deleteEmployee_shouldReturnDeletedEmployee() {
        Long employeeId = 1L;
        EmployeeDTORs deletedDto = new EmployeeDTORs();

        when(employeeService.logicalDeleteEmployee(employeeId)).thenReturn(deletedDto);

        ResponseEntity<EmployeeDTORs> response = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deletedDto, response.getBody());
        verify(employeeService, times(1)).logicalDeleteEmployee(employeeId);
    }

    @Test
    void getEmployeeWithHighestSalary_shouldReturnEmployee() {
        EmployeeDTORs employee = new EmployeeDTORs();
        when(employeeService.getEmployeeWithHighestSalary()).thenReturn(employee);

        ResponseEntity<EmployeeDTORs> response = employeeController.getEmployeeWithHighestSalary();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).getEmployeeWithHighestSalary();
    }

    @Test
    void getYoungestEmployee_shouldReturnEmployee() {
        EmployeeDTORs employee = new EmployeeDTORs();
        when(employeeService.getYoungestEmployee()).thenReturn(employee);

        ResponseEntity<EmployeeDTORs> response = employeeController.getYoungestEmployee();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).getYoungestEmployee();
    }

    @Test
    void countEmployeesJoinedLastMonth_shouldReturnCount() {
        Long count = 5L;
        when(employeeService.countEmployeesJoinedLastMonth()).thenReturn(count);

        ResponseEntity<Long> response = employeeController.countEmployeesJoinedLastMonth();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(count, response.getBody());
        verify(employeeService, times(1)).countEmployeesJoinedLastMonth();
    }
}
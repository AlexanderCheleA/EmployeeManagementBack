package com.hiberius.hiberius.controllers;

import com.hiberius.hiberius.dto.request.DepartmentDTORq;
import com.hiberius.hiberius.dto.response.DepartmentDTORs;
import com.hiberius.hiberius.services.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDepartment_shouldReturnListOfDepartments() {
        List<DepartmentDTORs> mockList = List.of(new DepartmentDTORs());
        when(departmentService.getDepartment()).thenReturn(mockList);

        ResponseEntity<List<DepartmentDTORs>> response = departmentController.getDepartment();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
        verify(departmentService, times(1)).getDepartment();
    }

    @Test
    void getDepartmentEmployee_shouldReturnListOfDepartmentsWithEmployees() {
        List<DepartmentDTORs> mockList = List.of(new DepartmentDTORs());
        when(departmentService.getDepartmentEmployee()).thenReturn(mockList);

        ResponseEntity<List<DepartmentDTORs>> response = departmentController.getDepartmentEmployee();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
        verify(departmentService, times(1)).getDepartmentEmployee();
    }

    @Test
    void createDepartment_shouldReturnCreatedDepartment() {
        DepartmentDTORq dto = new DepartmentDTORq();
        DepartmentDTORs createdDto = new DepartmentDTORs();

        when(departmentService.createDepartment(dto)).thenReturn(createdDto);

        ResponseEntity<DepartmentDTORs> response = departmentController.createDepartment(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
        verify(departmentService, times(1)).createDepartment(dto);
    }

    @Test
    void deleteDepartment_shouldReturnUpdatedDepartment() {
        Long departmentId = 1L;
        DepartmentDTORs updatedDto = new DepartmentDTORs();

        when(departmentService.logicalDeleteDepartment(departmentId)).thenReturn(updatedDto);

        ResponseEntity<DepartmentDTORs> response = departmentController.deleteDepartment(departmentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
        verify(departmentService, times(1)).logicalDeleteDepartment(departmentId);
    }
}
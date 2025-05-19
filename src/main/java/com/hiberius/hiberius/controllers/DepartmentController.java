package com.hiberius.hiberius.controllers;

import com.hiberius.hiberius.dto.request.DepartmentDTORq;
import com.hiberius.hiberius.dto.response.DepartmentDTORs;
import com.hiberius.hiberius.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@Validated
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTORs>> getDepartment() {
        return ResponseEntity.ok(departmentService.getDepartment());
    }

    @GetMapping("/employee")
    public ResponseEntity<List<DepartmentDTORs>> getDepartmentEmployee() {
        return ResponseEntity.ok(departmentService.getDepartmentEmployee());
    }

    @PostMapping("/create")
    public ResponseEntity<DepartmentDTORs> createDepartment(@RequestBody @Valid DepartmentDTORq dto) {
        DepartmentDTORs created = departmentService.createDepartment(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{departmentId}")
    public ResponseEntity<DepartmentDTORs> deleteDepartment(@PathVariable @Valid Long departmentId) {
        DepartmentDTORs updated = departmentService.logicalDeleteDepartment(departmentId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}

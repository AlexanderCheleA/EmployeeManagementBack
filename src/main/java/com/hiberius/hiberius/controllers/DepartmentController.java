package com.hiberius.hiberius.controllers;

import com.hiberius.hiberius.dto.DepartmentDTO;
import com.hiberius.hiberius.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<DepartmentDTO>> getDepartment() {
        return ResponseEntity.ok(departmentService.getDepartment());
    }

    @GetMapping("/employee")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentEmployee() {
        return ResponseEntity.ok(departmentService.getDepartmentEmployee());
    }

    @PostMapping("/create")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO dto) {
        DepartmentDTO created = departmentService.createDepartment(dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/delete/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable @Valid Long departmentId) {
        departmentService.logicalDeleteDepartment(departmentId);
        return ResponseEntity.ok().build();
    }
}

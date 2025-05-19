package com.hiberius.hiberius.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class DepartmentDTORs {
    private Long id;
    private String name;
    private String status;
    private List<EmployeeDTORs> employees;
}


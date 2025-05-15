package com.hiberius.hiberius.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El estado es obligatorio")
    private String status;
    private List<EmployeeDTO> employees;
}


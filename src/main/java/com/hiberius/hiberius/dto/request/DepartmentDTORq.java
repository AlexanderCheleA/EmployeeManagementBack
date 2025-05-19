package com.hiberius.hiberius.dto.request;

import com.hiberius.hiberius.dto.base.GlobalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class DepartmentDTORq{
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "El estado es obligatorio")
    private GlobalStatus status;

    private List<EmployeeDTORq> employees;
}


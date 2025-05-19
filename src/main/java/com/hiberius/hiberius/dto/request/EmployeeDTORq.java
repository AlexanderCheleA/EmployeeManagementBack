package com.hiberius.hiberius.dto.request;

import java.time.LocalDate;

import com.hiberius.hiberius.dto.base.GlobalStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmployeeDTORq {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad debe ser un valor positivo")
    private Integer age;
    private Double salary;
    private LocalDate initDate;
    private LocalDate endDate;
    private Long departmentId;
    private GlobalStatus status;
}


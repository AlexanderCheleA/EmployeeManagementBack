package com.hiberius.hiberius.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class EmployeeDTO {
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
    private String employeeStatus;
    private Long departmentId;
}


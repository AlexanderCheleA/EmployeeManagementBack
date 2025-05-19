package com.hiberius.hiberius.dto.response;

import java.time.LocalDate;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmployeeDTORs {
    private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private Double salary;
    private LocalDate initDate;
    private LocalDate endDate;
    private Long departmentId;
    private String status;
}


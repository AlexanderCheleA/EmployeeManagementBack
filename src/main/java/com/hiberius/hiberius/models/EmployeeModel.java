package com.hiberius.hiberius.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId", nullable = false)
    private Long id;

    private String name;
    private String lastName;
    private Integer age;
    private Double salary;

    private LocalDate initDate;
    private LocalDate endDate;

    private String employeeStatus;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentModel department;
}
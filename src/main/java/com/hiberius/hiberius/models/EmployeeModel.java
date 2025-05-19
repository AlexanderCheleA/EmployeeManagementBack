package com.hiberius.hiberius.models;

import com.hiberius.hiberius.dto.base.GlobalStatus;
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
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "salary", columnDefinition = "DOUBLE DEFAULT 0")
    private Double salary;

    @PrePersist
    @PreUpdate
    public void ensureSalaryNotNull() {
        if (this.salary == null) {
            this.salary = 0.0;
        }
    }

    @Column(name = "init_date")
    private LocalDate initDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 1)
    private GlobalStatus status;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentModel department;
}
package com.hiberius.hiberius.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "department")
public class DepartmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departmentId", nullable = false)
    private Long id;

    private String name;

    private String status;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<EmployeeModel> employees;
}

package com.hiberius.hiberius.models;

import com.hiberius.hiberius.dto.base.GlobalStatus;
import jakarta.persistence.*;
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
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 1)
    private GlobalStatus status;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<EmployeeModel> employees;
}

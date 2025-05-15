package com.hiberius.hiberius.repository;

import com.hiberius.hiberius.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    Optional<EmployeeModel> findTopByEmployeeStatusOrderBySalaryDesc(String status);

    Optional<EmployeeModel> findTopByEmployeeStatusOrderByAgeAsc(String status);

    Long countByInitDateBetween(LocalDate from, LocalDate to);
}

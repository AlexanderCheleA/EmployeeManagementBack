package com.hiberius.hiberius.repository;

import com.hiberius.hiberius.dto.base.GlobalStatus;
import com.hiberius.hiberius.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    Optional<EmployeeModel> findTopByStatusOrderBySalaryDesc(GlobalStatus status);

    Optional<EmployeeModel> findTopByStatusOrderByAgeAsc(GlobalStatus status);

    Long countByInitDateBetween(LocalDate from, LocalDate to);
}

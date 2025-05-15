package com.hiberius.hiberius.repository;

import com.hiberius.hiberius.models.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {

}

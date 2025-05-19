package com.hiberius.hiberius.repository;

import com.hiberius.hiberius.dto.base.GlobalStatus;
import com.hiberius.hiberius.models.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {

    List<DepartmentModel> findByNameAndStatus(String name, GlobalStatus status);

}

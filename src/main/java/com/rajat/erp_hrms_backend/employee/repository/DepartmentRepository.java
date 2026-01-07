package com.rajat.erp_hrms_backend.employee.repository;

import com.rajat.erp_hrms_backend.employee.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByNameAndIsDeletedFalse(String name);
}


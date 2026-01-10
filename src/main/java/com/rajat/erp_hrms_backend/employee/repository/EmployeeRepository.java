package com.rajat.erp_hrms_backend.employee.repository;

import com.rajat.erp_hrms_backend.employee.entity.Employee;
import com.rajat.erp_hrms_backend.common.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCodeAndIsDeletedFalse(String employeeCode);

    Optional<Employee> findByWorkEmailAndIsDeletedFalse(String workEmail);

    List<Employee> findByStatusAndIsDeletedFalse(EmployeeStatus status);

    List<Employee> findByManagerIdAndIsDeletedFalse(Long managerId);

    List<Employee> findByIsDeletedFalse();
}

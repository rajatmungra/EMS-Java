package com.rajat.erp_hrms_backend.leave.repository;

import com.rajat.erp_hrms_backend.leave.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {

    Optional<LeaveType> findByNameAndIsDeletedFalse(String name);
    boolean existsByNameIgnoreCaseAndIsDeletedFalse(String name);
}

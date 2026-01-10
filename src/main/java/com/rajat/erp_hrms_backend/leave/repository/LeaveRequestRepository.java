package com.rajat.erp_hrms_backend.leave.repository;

import com.rajat.erp_hrms_backend.leave.entity.LeaveRequest;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import com.rajat.erp_hrms_backend.common.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployee(Employee employee);

    @Query("""
        SELECT lr FROM LeaveRequest lr
        WHERE lr.employee = :employee
          AND lr.status = 'APPROVED'
          AND :startDate <= lr.endDate
          AND :endDate >= lr.startDate
    """)
    List<LeaveRequest> findOverlappingApprovedLeaves(
            @Param("employee") Employee employee,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<LeaveRequest> findByApprover(Employee approver);
}

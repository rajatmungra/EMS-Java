package com.rajat.erp_hrms_backend.leave.entity;

import com.rajat.erp_hrms_backend.common.base.BaseEntity;
import com.rajat.erp_hrms_backend.common.enums.LeaveStatus;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
public class LeaveRequest extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "leave_type_id")
    private LeaveType leaveType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer numberOfDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status = LeaveStatus.DRAFT;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private Employee approver;
}


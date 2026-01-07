package com.rajat.erp_hrms_backend.employee.entity;

import com.rajat.erp_hrms_backend.common.base.BaseEntity;
import com.rajat.erp_hrms_backend.common.enums.EmployeeStatus;
import com.rajat.erp_hrms_backend.common.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
    name = "employees",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "employeeCode"),
        @UniqueConstraint(columnNames = "workEmail")
    }
)
@Getter
@Setter
public class Employee extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private String employeeCode;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String workEmail;

    private String mobile;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    private LocalDate dateOfJoining;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status = EmployeeStatus.DRAFT;
}

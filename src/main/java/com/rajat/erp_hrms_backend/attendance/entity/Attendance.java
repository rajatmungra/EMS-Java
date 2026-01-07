package com.rajat.erp_hrms_backend.attendance.entity;

import com.rajat.erp_hrms_backend.calendar.entity.WorkingCalendar;
import com.rajat.erp_hrms_backend.common.base.BaseEntity;
import com.rajat.erp_hrms_backend.common.enums.AttendanceStatus;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "attendances",
    uniqueConstraints = @UniqueConstraint(
            columnNames = {"employee_id", "attendanceDate"}
    )
)
@Getter
@Setter
public class Attendance extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private Double workedHours;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status = AttendanceStatus.DRAFT;

    @ManyToOne(optional = false)
    @JoinColumn(name = "calendar_id")
    private WorkingCalendar calendar;
}


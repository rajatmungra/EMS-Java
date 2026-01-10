package com.rajat.erp_hrms_backend.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyAttendanceReport {

    private Long employeeId;
    private String employeeName;
    private int year;
    private int month;

    private long totalWorkingDays;
    private long presentDays;
    private double totalWorkedHours;
}

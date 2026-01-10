package com.rajat.erp_hrms_backend.report.service;

import com.rajat.erp_hrms_backend.report.dto.LeaveSummaryReport;
import com.rajat.erp_hrms_backend.report.dto.MonthlyAttendanceReport;

public interface ReportService {

    MonthlyAttendanceReport getMonthlyAttendanceReport(
            Long employeeId,
            int year,
            int month
    );

    LeaveSummaryReport getLeaveSummaryReport(
            Long employeeId,
            int year
    );

}

package com.rajat.erp_hrms_backend.report.service;

import com.rajat.erp_hrms_backend.common.exception.ResourceNotFoundException;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import com.rajat.erp_hrms_backend.employee.repository.EmployeeRepository;
import com.rajat.erp_hrms_backend.report.dto.LeaveSummaryReport;
import com.rajat.erp_hrms_backend.report.dto.MonthlyAttendanceReport;
import com.rajat.erp_hrms_backend.report.repository.AttendanceReportRepository;
import com.rajat.erp_hrms_backend.report.repository.LeaveReportRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final AttendanceReportRepository attendanceReportRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveReportRepository leaveReportRepository;

    @Override
    @PreAuthorize("""
    hasAnyRole('HR_OFFICER','MANAGER')
    or #employeeId == authentication.principal.employeeId
""")
    public MonthlyAttendanceReport getMonthlyAttendanceReport(
            Long employeeId,
            int year,
            int month
    ) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        Object rawResult =
                attendanceReportRepository.fetchMonthlySummary(
                        employeeId, start, end);

        Object[] result = (Object[]) rawResult;

        long totalWorkingDays = ((Number) result[0]).longValue();
        long presentDays = ((Number) result[1]).longValue();
        double totalWorkedHours = ((Number) result[2]).doubleValue();


        MonthlyAttendanceReport report = new MonthlyAttendanceReport();
        report.setEmployeeId(employeeId);
        report.setEmployeeName(employee.getFirstName());
        report.setYear(year);
        report.setMonth(month);

        report.setTotalWorkingDays(totalWorkingDays);
        report.setPresentDays(presentDays);
        report.setTotalWorkedHours(totalWorkedHours);

        return report;
    }

    @Override
    @PreAuthorize("""
    hasAnyRole('HR_OFFICER','MANAGER','ADMIN')
    or #employeeId == authentication.principal.employeeId
""")
    @Transactional(readOnly = true)
    public LeaveSummaryReport getLeaveSummaryReport(
            Long employeeId,
            int year
    ) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        Tuple t = leaveReportRepository.fetchLeaveSummary(
                employeeId, start, end);

        LeaveSummaryReport report = new LeaveSummaryReport();
        report.setEmployeeId(employeeId);
        report.setEmployeeName(employee.getFirstName());
        report.setYear(year);

        report.setTotalRequests(
                ((Number) t.get("total")).longValue());
        report.setApprovedLeaves(
                ((Number) t.get("approved")).longValue());
        report.setRejectedLeaves(
                ((Number) t.get("rejected")).longValue());
        report.setPendingLeaves(
                ((Number) t.get("pending")).longValue());

        return report;
    }


}

package com.rajat.erp_hrms_backend.report.controller;

import com.rajat.erp_hrms_backend.common.dto.ApiResponse;
import com.rajat.erp_hrms_backend.report.dto.LeaveSummaryReport;
import com.rajat.erp_hrms_backend.report.dto.MonthlyAttendanceReport;
import com.rajat.erp_hrms_backend.report.pdf.AttendanceReportPdfGenerator;
import com.rajat.erp_hrms_backend.report.pdf.LeaveSummaryPdfGenerator;
import com.rajat.erp_hrms_backend.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/attendance/monthly")
    public ApiResponse<MonthlyAttendanceReport> monthlyAttendance(
            @RequestParam Long employeeId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return new ApiResponse<>(
                true,
                "Monthly attendance report generated",
                reportService.getMonthlyAttendanceReport(employeeId, year, month)
        );
    }

    @GetMapping("/attendance/monthly/pdf")
    public ResponseEntity<byte[]> monthlyAttendancePdf(
            @RequestParam Long employeeId,
            @RequestParam int year,
            @RequestParam int month
    ) {

        MonthlyAttendanceReport report =
                reportService.getMonthlyAttendanceReport(employeeId, year, month);

        byte[] pdfBytes = AttendanceReportPdfGenerator.generate(report);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=attendance-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping("/leaves/summary")
    public ApiResponse<LeaveSummaryReport> leaveSummary(
            @RequestParam Long employeeId,
            @RequestParam int year
    ) {
        return new ApiResponse<>(
                true,
                "Leave summary report generated",
                reportService.getLeaveSummaryReport(employeeId, year)
        );
    }

    @GetMapping("/leaves/summary/pdf")
    public ResponseEntity<byte[]> leaveSummaryPdf(
            @RequestParam Long employeeId,
            @RequestParam int year
    ) {

        LeaveSummaryReport report =
                reportService.getLeaveSummaryReport(employeeId, year);

        byte[] pdf = LeaveSummaryPdfGenerator.generate(report);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=leave-summary-" + year + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }



}

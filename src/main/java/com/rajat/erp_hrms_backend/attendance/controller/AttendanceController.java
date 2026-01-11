package com.rajat.erp_hrms_backend.attendance.controller;

import com.rajat.erp_hrms_backend.attendance.dto.AttendanceResponse;
import com.rajat.erp_hrms_backend.attendance.service.AttendanceService;
import com.rajat.erp_hrms_backend.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/{employeeId}/check-in")
    public ApiResponse<AttendanceResponse> checkIn(@PathVariable Long employeeId) {
        return new ApiResponse<>(
                true,
                "Checked in successfully",
                attendanceService.checkIn(employeeId)
        );
    }

    @PostMapping("/{employeeId}/check-out")
    public ApiResponse<AttendanceResponse> checkOut(@PathVariable Long employeeId) {
        return new ApiResponse<>(
                true,
                "Checked out successfully",
                attendanceService.checkOut(employeeId)
        );
    }
}

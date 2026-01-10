package com.rajat.erp_hrms_backend.attendance.service;

import com.rajat.erp_hrms_backend.attendance.dto.AttendanceResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AttendanceService {

    @PreAuthorize(
            "hasRole('EMPLOYEE') and #employeeId == authentication.principal.employeeId"
    )
    AttendanceResponse checkIn(Long employeeId);

    @PreAuthorize(
            "hasRole('EMPLOYEE') and #employeeId == authentication.principal.employeeId"
    )
    AttendanceResponse checkOut(Long employeeId);
}

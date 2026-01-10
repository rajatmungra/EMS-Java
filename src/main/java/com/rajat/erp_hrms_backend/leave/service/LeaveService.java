package com.rajat.erp_hrms_backend.leave.service;

import com.rajat.erp_hrms_backend.leave.dto.LeaveApplyRequest;
import com.rajat.erp_hrms_backend.leave.dto.LeaveResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LeaveService {

    @PreAuthorize(
            "hasRole('MANAGER') or (hasRole('EMPLOYEE') and #employeeId == authentication.principal.employeeId)"
    )
    LeaveResponse applyLeave(Long employeeId, LeaveApplyRequest request);

    @PreAuthorize("hasAnyRole('MANAGER', 'HR_OFFICER')")
    LeaveResponse approveLeave(Long leaveId, Long approverId);

    @PreAuthorize("hasAnyRole('MANAGER', 'HR_OFFICER')")
    LeaveResponse rejectLeave(Long leaveId, Long approverId);
}

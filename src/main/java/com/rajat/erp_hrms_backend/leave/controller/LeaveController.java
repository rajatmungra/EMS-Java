package com.rajat.erp_hrms_backend.leave.controller;

import com.rajat.erp_hrms_backend.common.dto.ApiResponse;
import com.rajat.erp_hrms_backend.leave.dto.LeaveApplyRequest;
import com.rajat.erp_hrms_backend.leave.dto.LeaveResponse;
import com.rajat.erp_hrms_backend.leave.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    // ---------------- APPLY LEAVE ----------------
    @PostMapping("/{employeeId}/apply")
    public ApiResponse<LeaveResponse> applyLeave(
            @PathVariable Long employeeId,
            @Valid @RequestBody LeaveApplyRequest request
    ) {
        return new ApiResponse<>(
                true,
                "Leave applied successfully",
                leaveService.applyLeave(employeeId, request)
        );
    }

    // ---------------- APPROVE LEAVE ----------------
    @PatchMapping("/{leaveId}/approve")
    public ApiResponse<LeaveResponse> approveLeave(
            @PathVariable Long leaveId,
            @RequestParam Long approverId
    ) {
        return new ApiResponse<>(
                true,
                "Leave approved successfully",
                leaveService.approveLeave(leaveId, approverId)
        );
    }

    // ---------------- REJECT LEAVE ----------------
    @PatchMapping("/{leaveId}/reject")
    public ApiResponse<LeaveResponse> rejectLeave(
            @PathVariable Long leaveId,
            @RequestParam Long approverId
    ) {
        return new ApiResponse<>(
                true,
                "Leave rejected successfully",
                leaveService.rejectLeave(leaveId, approverId)
        );
    }
}

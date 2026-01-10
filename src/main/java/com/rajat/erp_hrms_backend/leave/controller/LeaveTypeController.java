package com.rajat.erp_hrms_backend.leave.controller;

import com.rajat.erp_hrms_backend.common.dto.ApiResponse;
import com.rajat.erp_hrms_backend.leave.dto.LeaveTypeCreateRequest;
import com.rajat.erp_hrms_backend.leave.dto.LeaveTypeResponse;
import com.rajat.erp_hrms_backend.leave.service.LeaveTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leave-types")
@RequiredArgsConstructor
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    @PostMapping
    public ApiResponse<LeaveTypeResponse> create(
            @Valid @RequestBody LeaveTypeCreateRequest request
    ) {
        return new ApiResponse<>(
                true,
                "Leave type created successfully",
                leaveTypeService.createLeaveType(request)
        );
    }
}

package com.rajat.erp_hrms_backend.leave.service;

import com.rajat.erp_hrms_backend.leave.dto.LeaveTypeCreateRequest;
import com.rajat.erp_hrms_backend.leave.dto.LeaveTypeResponse;

public interface LeaveTypeService {

    LeaveTypeResponse createLeaveType(LeaveTypeCreateRequest request);
}

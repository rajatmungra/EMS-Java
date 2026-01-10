package com.rajat.erp_hrms_backend.leave.mapper;

import com.rajat.erp_hrms_backend.leave.dto.LeaveResponse;
import com.rajat.erp_hrms_backend.leave.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaveMapper {

    @Mapping(target = "leaveType", source = "leaveType.name")
    @Mapping(
            target = "approverName",
            expression =
                    "java(leave.getApprover() != null ? leave.getApprover().getFirstName() : null)"
    )
    LeaveResponse toResponse(LeaveRequest leave);
}

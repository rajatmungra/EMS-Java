package com.rajat.erp_hrms_backend.leave.service.impl;

import com.rajat.erp_hrms_backend.common.exception.BusinessException;
import com.rajat.erp_hrms_backend.leave.dto.LeaveTypeCreateRequest;
import com.rajat.erp_hrms_backend.leave.dto.LeaveTypeResponse;
import com.rajat.erp_hrms_backend.leave.entity.LeaveType;
import com.rajat.erp_hrms_backend.leave.repository.LeaveTypeRepository;
import com.rajat.erp_hrms_backend.leave.service.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    @Override
    @PreAuthorize("hasAnyRole('HR_OFFICER','ADMIN')")
    public LeaveTypeResponse createLeaveType(LeaveTypeCreateRequest request) {

        if (leaveTypeRepository.existsByNameIgnoreCaseAndIsDeletedFalse(request.getName())) {
            throw new BusinessException(
                    "Leave type already exists: " + request.getName()
            );
        }

        LeaveType leaveType = new LeaveType();
        leaveType.setName(request.getName().trim());
        leaveType.setMaxPerYear(request.getMaxPerYear());
        leaveType.setIsActive(true);

        LeaveType saved = leaveTypeRepository.save(leaveType);

        LeaveTypeResponse response = new LeaveTypeResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setMaxPerYear(saved.getMaxPerYear());
        response.setIsActive(saved.getIsActive());

        return response;
    }
}

package com.rajat.erp_hrms_backend.leave.service.impl;

import com.rajat.erp_hrms_backend.common.enums.LeaveStatus;
import com.rajat.erp_hrms_backend.common.exception.BusinessException;
import com.rajat.erp_hrms_backend.common.exception.ResourceNotFoundException;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import com.rajat.erp_hrms_backend.employee.repository.EmployeeRepository;
import com.rajat.erp_hrms_backend.leave.dto.LeaveApplyRequest;
import com.rajat.erp_hrms_backend.leave.dto.LeaveResponse;
import com.rajat.erp_hrms_backend.leave.entity.LeaveRequest;
import com.rajat.erp_hrms_backend.leave.entity.LeaveType;
import com.rajat.erp_hrms_backend.leave.mapper.LeaveMapper;
import com.rajat.erp_hrms_backend.leave.repository.LeaveRequestRepository;
import com.rajat.erp_hrms_backend.leave.repository.LeaveTypeRepository;
import com.rajat.erp_hrms_backend.leave.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRequestRepository leaveRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveMapper leaveMapper;

    @Override
    public LeaveResponse applyLeave(Long employeeId, LeaveApplyRequest request) {

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BusinessException("Invalid leave date range");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));

        if (!leaveRepository.findOverlappingApprovedLeaves(
                employee,
                request.getStartDate(),
                request.getEndDate()
        ).isEmpty()) {
            throw new BusinessException("Leave overlaps with existing approved leave");
        }

        int days =
                (int) ChronoUnit.DAYS.between(
                        request.getStartDate(),
                        request.getEndDate()
                ) + 1;

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(employee);
        leave.setLeaveType(leaveType);
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setNumberOfDays(days);
        leave.setStatus(LeaveStatus.SUBMITTED);

        return leaveMapper.toResponse(leaveRepository.save(leave));
    }

    @Override
    public LeaveResponse approveLeave(Long leaveId, Long approverId) {

        LeaveRequest leave = getLeave(leaveId);
        Employee approver = getEmployee(approverId);


        leave.setStatus(LeaveStatus.APPROVED);
        leave.setApprover(approver);

        return leaveMapper.toResponse(leaveRepository.save(leave));
    }

    @Override
    public LeaveResponse rejectLeave(Long leaveId, Long approverId) {

        LeaveRequest leave = getLeave(leaveId);
        leave.setStatus(LeaveStatus.REJECTED);
        leave.setApprover(getEmployee(approverId));

        return leaveMapper.toResponse(leaveRepository.save(leave));
    }

    private LeaveRequest getLeave(Long leaveId) {
        return leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
    }

    private Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }
}

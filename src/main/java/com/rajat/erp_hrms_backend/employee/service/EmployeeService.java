package com.rajat.erp_hrms_backend.employee.service;

import com.rajat.erp_hrms_backend.employee.dto.EmployeeCreateRequest;
import com.rajat.erp_hrms_backend.employee.dto.EmployeeResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface EmployeeService {

    @PreAuthorize("hasRole('HR_OFFICER')")
    EmployeeResponse createEmployee(EmployeeCreateRequest request);

    @PreAuthorize("hasRole('HR_OFFICER')")
    EmployeeResponse activateEmployee(Long employeeId);

    @PreAuthorize("hasRole('HR_OFFICER')")
    EmployeeResponse deactivateEmployee(Long employeeId);

    List<EmployeeResponse> getAllEmployees();
}

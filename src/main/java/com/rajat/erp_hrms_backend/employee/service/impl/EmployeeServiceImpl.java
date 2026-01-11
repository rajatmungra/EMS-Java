package com.rajat.erp_hrms_backend.employee.service.impl;

import com.rajat.erp_hrms_backend.common.enums.EmployeeStatus;
import com.rajat.erp_hrms_backend.common.exception.BusinessException;
import com.rajat.erp_hrms_backend.common.exception.ResourceNotFoundException;
import com.rajat.erp_hrms_backend.employee.dto.EmployeeCreateRequest;
import com.rajat.erp_hrms_backend.employee.dto.EmployeeResponse;
import com.rajat.erp_hrms_backend.employee.entity.Department;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import com.rajat.erp_hrms_backend.employee.entity.JobTitle;
import com.rajat.erp_hrms_backend.employee.mapper.EmployeeMapper;
import com.rajat.erp_hrms_backend.employee.repository.DepartmentRepository;
import com.rajat.erp_hrms_backend.employee.repository.EmployeeRepository;
import com.rajat.erp_hrms_backend.employee.repository.JobTitleRepository;
import com.rajat.erp_hrms_backend.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobTitleRepository jobTitleRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    public EmployeeResponse createEmployee(EmployeeCreateRequest request) {

        Employee employee = employeeMapper.toEntity(request);

        // Department
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            employee.setDepartment(department);
        }

        // Job Title
        if (request.getJobTitleId() != null) {
            JobTitle jobTitle = jobTitleRepository.findById(request.getJobTitleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Job title not found"));
            employee.setJobTitle(jobTitle);
        }

        // Manager
        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

            if (manager.getStatus() != EmployeeStatus.ACTIVE) {
                throw new BusinessException("Manager must be ACTIVE");
            }
            employee.setManager(manager);
        }

        employee.setStatus(EmployeeStatus.DRAFT);
        employee.setEmployeeCode(generateEmployeeCode());

        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }


    @Override
    @PreAuthorize("hasRole('HR_OFFICER')")
    public EmployeeResponse activateEmployee(Long employeeId) {
        Employee employee = getEmployee(employeeId);

        if (employee.getStatus() != EmployeeStatus.DRAFT) {
            throw new BusinessException("Only DRAFT employees can be activated");
        }

        employee.setStatus(EmployeeStatus.ACTIVE);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }


    @Override
    public EmployeeResponse deactivateEmployee(Long employeeId) {
        Employee employee = getEmployee(employeeId);

        employee.setStatus(EmployeeStatus.INACTIVE);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findByIsDeletedFalse();
        return employees.stream().map(employeeMapper::toResponse).toList();
    }


    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    private String generateEmployeeCode() {
        return "EMP-" + System.currentTimeMillis();
    }
}

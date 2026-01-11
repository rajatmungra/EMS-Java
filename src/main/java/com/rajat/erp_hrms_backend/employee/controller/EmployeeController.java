package com.rajat.erp_hrms_backend.employee.controller;

import com.rajat.erp_hrms_backend.common.dto.ApiResponse;
import com.rajat.erp_hrms_backend.employee.dto.EmployeeCreateRequest;
import com.rajat.erp_hrms_backend.employee.dto.EmployeeResponse;
import com.rajat.erp_hrms_backend.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ApiResponse<List<EmployeeResponse>> getAllEmployees(){
        return new ApiResponse<>(
                true,
                "All Employees fetched successfully",
                employeeService.getAllEmployees()
        );
    }

    @PostMapping
    public ApiResponse<EmployeeResponse> create(
            @Valid @RequestBody EmployeeCreateRequest request
    ) {
        return new ApiResponse<>(
                true,
                "Employee created successfully",
                employeeService.createEmployee(request)
        );
    }

    @PatchMapping("/{id}/activate")
    public ApiResponse<EmployeeResponse> activate(@PathVariable Long id) {
        return new ApiResponse<>(
                true,
                "Employee activated successfully",
                employeeService.activateEmployee(id)
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ApiResponse<EmployeeResponse> deactivate(@PathVariable Long id) {
        return new ApiResponse<>(
                true,
                "Employee deactivated successfully",
                employeeService.deactivateEmployee(id)
        );
    }
}

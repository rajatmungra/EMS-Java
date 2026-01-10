package com.rajat.erp_hrms_backend.employee.dto;

import com.rajat.erp_hrms_backend.common.enums.EmploymentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeCreateRequest {

    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    private String workEmail;

    @NotNull
    private EmploymentType employmentType;

    @NotNull
    private LocalDate dateOfJoining;

    private Long departmentId;
    private Long jobTitleId;
    private Long managerId;
}

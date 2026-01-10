package com.rajat.erp_hrms_backend.employee.dto;

import com.rajat.erp_hrms_backend.common.enums.EmployeeStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String workEmail;
    private EmployeeStatus status;

    private String departmentName;
    private String jobTitle;
    private String managerName;
}

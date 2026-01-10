package com.rajat.erp_hrms_backend.leave.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveTypeResponse {

    private Long id;
    private String name;
    private Integer maxPerYear;
    private Boolean isActive;
}

package com.rajat.erp_hrms_backend.leave.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveTypeCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Integer maxPerYear;
}

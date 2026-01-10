package com.rajat.erp_hrms_backend.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveSummaryReport {

    private Long employeeId;
    private String employeeName;
    private int year;

    private long totalRequests;
    private long approvedLeaves;
    private long rejectedLeaves;
    private long pendingLeaves;
}

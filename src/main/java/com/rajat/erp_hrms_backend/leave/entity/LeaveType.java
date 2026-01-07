package com.rajat.erp_hrms_backend.leave.entity;

import com.rajat.erp_hrms_backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "leave_types",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@Getter
@Setter
public class LeaveType extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer maxPerYear;

    @Column(nullable = false)
    private Boolean isActive = true;
}


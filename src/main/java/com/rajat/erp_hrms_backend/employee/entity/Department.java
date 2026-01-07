package com.rajat.erp_hrms_backend.employee.entity;

import com.rajat.erp_hrms_backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "departments",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@Getter
@Setter
public class Department extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isActive = true;
}

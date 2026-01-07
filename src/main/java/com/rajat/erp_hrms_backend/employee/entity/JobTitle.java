package com.rajat.erp_hrms_backend.employee.entity;

import com.rajat.erp_hrms_backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "job_titles",
    uniqueConstraints = @UniqueConstraint(columnNames = "title")
)
@Getter
@Setter
public class JobTitle extends BaseEntity {

    @Column(nullable = false)
    private String title;

    private String level;
}

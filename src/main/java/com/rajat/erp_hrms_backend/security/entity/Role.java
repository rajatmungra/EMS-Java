package com.rajat.erp_hrms_backend.security.entity;

import com.rajat.erp_hrms_backend.common.base.BaseEntity;
import com.rajat.erp_hrms_backend.common.enums.RoleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "roles",
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@Getter
@Setter
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName name;
}

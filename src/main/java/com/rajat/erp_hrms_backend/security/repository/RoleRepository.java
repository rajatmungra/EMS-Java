package com.rajat.erp_hrms_backend.security.repository;

import com.rajat.erp_hrms_backend.security.entity.Role;
import com.rajat.erp_hrms_backend.common.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}

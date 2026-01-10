package com.rajat.erp_hrms_backend.security.repository;

import com.rajat.erp_hrms_backend.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    Optional<User> findByEmployeeIdAndIsDeletedFalse(Long employeeId);
}

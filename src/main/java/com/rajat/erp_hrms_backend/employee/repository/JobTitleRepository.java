package com.rajat.erp_hrms_backend.employee.repository;

import com.rajat.erp_hrms_backend.employee.entity.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {

    Optional<JobTitle> findByTitleAndIsDeletedFalse(String title);
}

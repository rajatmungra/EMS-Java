package com.rajat.erp_hrms_backend.calendar.repository;

import com.rajat.erp_hrms_backend.calendar.entity.WorkingCalendar;
import com.rajat.erp_hrms_backend.common.enums.CalendarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkingCalendarRepository extends JpaRepository<WorkingCalendar, Long> {

    Optional<WorkingCalendar> findByStatus(CalendarStatus status);
}

package com.rajat.erp_hrms_backend.report.repository;

import com.rajat.erp_hrms_backend.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;

public interface AttendanceReportRepository extends Repository<Attendance, Long> {

    @Query("""
    select 
        count(a.id),
        count(case when a.status = 'CHECKED_OUT' then 1 end),
        coalesce(sum(a.workedHours), 0)
    from Attendance a
    where a.employee.id = :employeeId
      and a.attendanceDate between :start and :end
""")
    Object fetchMonthlySummary(
            Long employeeId,
            LocalDate start,
            LocalDate end
    );

}

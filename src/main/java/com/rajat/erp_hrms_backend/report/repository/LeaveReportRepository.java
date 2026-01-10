package com.rajat.erp_hrms_backend.report.repository;

import com.rajat.erp_hrms_backend.leave.entity.LeaveRequest;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;

public interface LeaveReportRepository
        extends Repository<LeaveRequest, Long> {

    @Query("""
    select
        count(l.id) as total,
        coalesce(sum(case when l.status = 'APPROVED' then 1 else 0 end), 0) as approved,
        coalesce(sum(case when l.status = 'REJECTED' then 1 else 0 end), 0) as rejected,
        coalesce(sum(case when l.status = 'PENDING' then 1 else 0 end), 0) as pending
    from LeaveRequest l
    where l.employee.id = :employeeId
      and l.startDate <= :end
      and l.endDate >= :start
""")
    Tuple fetchLeaveSummary(
            Long employeeId,
            LocalDate start,
            LocalDate end
    );

}

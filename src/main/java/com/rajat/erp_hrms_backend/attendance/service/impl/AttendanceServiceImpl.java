package com.rajat.erp_hrms_backend.attendance.service.impl;

import com.rajat.erp_hrms_backend.attendance.dto.AttendanceResponse;
import com.rajat.erp_hrms_backend.attendance.entity.Attendance;
import com.rajat.erp_hrms_backend.attendance.mapper.AttendanceMapper;
import com.rajat.erp_hrms_backend.attendance.repository.AttendanceRepository;
import com.rajat.erp_hrms_backend.attendance.service.AttendanceService;
import com.rajat.erp_hrms_backend.calendar.entity.WorkingCalendar;
import com.rajat.erp_hrms_backend.calendar.repository.WorkingCalendarRepository;
import com.rajat.erp_hrms_backend.common.enums.AttendanceStatus;
import com.rajat.erp_hrms_backend.common.enums.CalendarStatus;
import com.rajat.erp_hrms_backend.common.exception.BusinessException;
import com.rajat.erp_hrms_backend.common.exception.ResourceNotFoundException;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import com.rajat.erp_hrms_backend.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkingCalendarRepository calendarRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    @PreAuthorize("hasRole('EMPLOYEE') and #employeeId == authentication.principal.employeeId")
    public AttendanceResponse checkIn(Long employeeId) {

        Employee employee = getActiveEmployee(employeeId);
        LocalDate today = LocalDate.now();

        attendanceRepository.findByEmployeeAndAttendanceDate(employee, today)
                .ifPresent(a -> {
                    throw new BusinessException("Employee already checked in today");
                });

        WorkingCalendar calendar = calendarRepository
                .findByStatus(CalendarStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException("No active working calendar configured"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.CHECKED_IN);
        attendance.setCalendar(calendar);

        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceResponse checkOut(Long employeeId) {

        Employee employee = getActiveEmployee(employeeId);
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository
                .findByEmployeeAndAttendanceDate(employee, today)
                .orElseThrow(() -> new BusinessException("No check-in found for today"));

        if (attendance.getStatus() != AttendanceStatus.CHECKED_IN) {
            throw new BusinessException("Invalid check-out attempt");
        }

        LocalDateTime now = LocalDateTime.now();
        attendance.setCheckOutTime(now);

        double workedHours =
                Duration.between(attendance.getCheckInTime(), now).toMinutes() / 60.0;

        attendance.setWorkedHours(workedHours);
        attendance.setStatus(AttendanceStatus.CHECKED_OUT);

        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    private Employee getActiveEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (employee.getStatus() != com.rajat.erp_hrms_backend.common.enums.EmployeeStatus.ACTIVE) {
            throw new BusinessException("Inactive employee cannot mark attendance");
        }
        return employee;
    }
}

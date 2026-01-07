package com.rajat.erp_hrms_backend.calendar.entity;

import com.rajat.erp_hrms_backend.common.base.BaseEntity;
import com.rajat.erp_hrms_backend.common.enums.CalendarStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "working_calendars")
@Getter
@Setter
public class WorkingCalendar extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(
            name = "working_calendar_days",
            joinColumns = @JoinColumn(name = "calendar_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private Set<DayOfWeek> workingDays;

    @Column(nullable = false)
    private Integer hoursPerDay;

    @Column(nullable = false)
    private LocalDate effectiveFrom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CalendarStatus status = CalendarStatus.DRAFT;
}


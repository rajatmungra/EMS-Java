package com.rajat.erp_hrms_backend.calendar;

import com.rajat.erp_hrms_backend.calendar.entity.WorkingCalendar;
import com.rajat.erp_hrms_backend.calendar.repository.WorkingCalendarRepository;
import com.rajat.erp_hrms_backend.common.enums.CalendarStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class WorkingCalendarInitializer {

    private final WorkingCalendarRepository repository;

    @PostConstruct
    public void init() {

        if (repository.count() == 0) {

            WorkingCalendar calendar = new WorkingCalendar();
            calendar.setName("Default Working Calendar");
            calendar.setWorkingDays(Set.of(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
            ));
            calendar.setHoursPerDay(8);
            calendar.setEffectiveFrom(LocalDate.now());
            calendar.setStatus(CalendarStatus.ACTIVE);

            repository.save(calendar);
        }
    }
}

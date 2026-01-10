package com.rajat.erp_hrms_backend.attendance.mapper;

import com.rajat.erp_hrms_backend.attendance.dto.AttendanceResponse;
import com.rajat.erp_hrms_backend.attendance.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceResponse toResponse(Attendance attendance);
}

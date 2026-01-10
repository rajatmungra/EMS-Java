package com.rajat.erp_hrms_backend.employee.mapper;

import com.rajat.erp_hrms_backend.employee.dto.EmployeeCreateRequest;
import com.rajat.erp_hrms_backend.employee.dto.EmployeeResponse;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "jobTitle", ignore = true)
    @Mapping(target = "manager", ignore = true)
    Employee toEntity(EmployeeCreateRequest request);

    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "jobTitle", source = "jobTitle.title")
    @Mapping(
            target = "managerName",
            expression = "java(employee.getManager() != null ? employee.getManager().getFirstName() : null)"
    )
    EmployeeResponse toResponse(Employee employee);
}

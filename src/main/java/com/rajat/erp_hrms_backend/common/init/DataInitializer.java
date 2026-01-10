package com.rajat.erp_hrms_backend.common.init;

import com.rajat.erp_hrms_backend.common.enums.EmployeeStatus;
import com.rajat.erp_hrms_backend.common.enums.EmploymentType;
import com.rajat.erp_hrms_backend.common.enums.RoleName;
import com.rajat.erp_hrms_backend.employee.entity.Employee;
import com.rajat.erp_hrms_backend.employee.repository.EmployeeRepository;
import com.rajat.erp_hrms_backend.security.entity.Role;
import com.rajat.erp_hrms_backend.security.entity.User;
import com.rajat.erp_hrms_backend.security.repository.RoleRepository;
import com.rajat.erp_hrms_backend.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // 1️⃣ Create roles
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(roleName);
                        return roleRepository.save(role);
                    });
        }

        // 2️⃣ Create HR employee + user
        createUserIfNotExists(
                "hr@erp.com",
                "password",
                RoleName.HR_OFFICER,
                "HR",
                null
        );

        // 3️⃣ Create Manager
        Employee manager = createUserIfNotExists(
                "manager@erp.com",
                "password",
                RoleName.MANAGER,
                "Manager",
                null
        );

        // 4️⃣ Create Employee under Manager
        createUserIfNotExists(
                "employee@erp.com",
                "password",
                RoleName.EMPLOYEE,
                "Employee",
                manager
        );
    }

    private Employee createUserIfNotExists(
            String username,
            String rawPassword,
            RoleName roleName,
            String firstName,
            Employee manager
    ) {

        return userRepository.findByUsernameAndIsDeletedFalse(username)
                .map(User::getEmployee)
                .orElseGet(() -> {

                    // Employee
                    Employee employee = new Employee();
                    employee.setFirstName(firstName);
                    employee.setWorkEmail(username);
                    employee.setEmploymentType(EmploymentType.FULL_TIME);
                    employee.setDateOfJoining(LocalDate.now());
                    employee.setStatus(EmployeeStatus.ACTIVE);
                    employee.setEmployeeCode("EMP-" + System.currentTimeMillis());
                    employee.setManager(manager);

                    employee = employeeRepository.save(employee);

                    // Role
                    Role role = roleRepository.findByName(roleName)
                            .orElseThrow();

                    // User
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(passwordEncoder.encode(rawPassword));
                    user.setEmployee(employee);
                    user.setEnabled(true);
                    user.setRoles(Set.of(role));

                    userRepository.save(user);

                    return employee;
                });
    }
}

package com.ardi.server.dto.employee.response;

import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.dto.employee.JobTitle;
import com.ardi.server.dto.employee.WorkLocation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ResponseEmployee {

    public record Summary (
        long idx,
        String name,
        String employeeNumber,
        String hireYear,
        String jobTitle,
        long currentAnnualSalary,
        String employmentStatus,
        String workLocation
    ){}

    public record Detail (
        long idx,
        String name,
        String employeeNumber,
        String hireYear,
        String jobTitle,
        long currentAnnualSalary,
        String employmentStatus,
        String workLocation,
        String deptName,

        // detail
        String email,
        String phoneNumber,
        String remark
    ){}
}

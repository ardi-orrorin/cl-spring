package com.ardi.server.dto.employee.response;

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
}

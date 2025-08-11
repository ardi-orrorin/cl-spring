package com.ardi.server.dto.employee.response;

public class ResponseEmployee {

    public record Summary (
        long idx,
        String name,
        String employeeNumber,
        String jobTitle,
        long currentAnnualSalary,
        String employmentStatus,
        String workLocation
    ){}
}

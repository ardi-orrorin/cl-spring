package com.ardi.server.dto.employee.response;

import com.ardi.server.dto.employee.EmploymentStatus;

import java.util.List;

public class ResponseEmployeeEvaluation {
    public record List(
        long idx,
        long employeeIdx,
        String employeeName,
        String hireYear,
        String employeeNumber,
        long currentAnnualSalary,
        java.util.List<Long> evaluationItemIdxs,
        int increaseRate,
        int totalScore
    ) {}

    public record EmployeeEvaluation (
        long employeeEvaluationIdx,
        String employeeName,
        String employmentStatus,
        long currentAnnualSalary,
        long nextAnnualSalary,
        int increaseRate,
        int totalScore
    ){}

    public record Report (
        int resignCount,
        java.util.List<EmployeeEvaluation> employeeEvaluations
    ){}
}

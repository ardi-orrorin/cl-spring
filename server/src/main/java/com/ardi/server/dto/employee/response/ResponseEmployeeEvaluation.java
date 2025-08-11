package com.ardi.server.dto.employee.response;

public class ResponseEmployeeEvaluation {
    public record List(
        long idx,
        String employeeName,
        String hireYear,
        String employeeNumber,
        java.util.List<Long> evaluationItemIdxs,
        Long increaseRate,
        int totalScore

        //fixme: 프로젝트 메모 처리 어떻게 할까
//        List<ResponseEmployeeProject> projects
    ) {}
}

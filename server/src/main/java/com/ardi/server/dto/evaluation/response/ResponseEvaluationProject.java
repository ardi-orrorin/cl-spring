package com.ardi.server.dto.evaluation.response;

public class ResponseEvaluationProject {
    public record Project(
        long idx,
        long employeesEvaluationIdx,
        String name,
        String participationPeriod,
        String remark
    ){}
}

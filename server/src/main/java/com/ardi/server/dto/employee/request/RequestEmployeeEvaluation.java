package com.ardi.server.dto.employee.request;

import java.util.List;

public class RequestEmployeeEvaluation {
    public record Update(
        long idx,
        List<Long> evaluationItemIdxs,
        Long increaseRate,
        List<RequestEmployeeProject.Create> projects
    ) { }
}

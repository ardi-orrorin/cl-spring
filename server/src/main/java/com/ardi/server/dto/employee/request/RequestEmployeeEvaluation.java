package com.ardi.server.dto.employee.request;

import java.util.List;

public class RequestEmployeeEvaluation {
    public record Update(
        long idx,
        List<Long> evaluationItemIdxs,
        int increaseRate,
        List<RequestEmployeeProject.Create> projects
    ) { }
}

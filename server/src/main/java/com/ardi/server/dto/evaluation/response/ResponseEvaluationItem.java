package com.ardi.server.dto.evaluation.response;

public class ResponseEvaluationItem {

    public record Summary(
        long idx,
        String evaluationCategoryName,
        String name,
        int score,
        boolean isUsed
    ) {}

    public record Select(
        long idx,
        String name,
        int score
    ){}
}

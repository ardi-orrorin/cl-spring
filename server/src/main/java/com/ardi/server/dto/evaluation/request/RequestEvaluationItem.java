package com.ardi.server.dto.evaluation.request;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;

import java.time.Instant;

public class RequestEvaluationItem {

    public record Search (
        String name,
        long evaluationCategoryIdx
    ){}

    public record Create(
            String name,
            int score,
            boolean isUsed,
            String remark,
            long evaluationCategoryIdx
    ) {
        public EvaluationItemEntity toEntity() {
            EvaluationItemEntity entity = new EvaluationItemEntity();
            entity.setName(name);
            entity.setScore(score);
            entity.setUsed(isUsed);
            entity.setRemark(remark);
            entity.setCreatedAt(Instant.now());
            entity.setUpdatedAt(Instant.now());

            return entity;
        }
    }
}

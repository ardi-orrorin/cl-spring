package com.ardi.server.dto.evaluation.request;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;

import java.time.Instant;

public class RequestEvaluationItem {

    public record Search (
        String name,
        long evaluationCategoryIdx
    ){}

    public record Create(
        long idx,
        String name,
        int score,
        boolean isUsed,
        String remark,
        long evaluationCategoryIdx
    ) {
        public EvaluationItemEntity toEntity() {
            EvaluationItemEntity entity = new EvaluationItemEntity();
            update(entity);
            entity.setCreatedAt(Instant.now());

            return entity;
        }

        public void update(EvaluationItemEntity entity) {
            entity.setName(name);
            entity.setScore(score);
            entity.setUsed(isUsed);
            entity.setRemark(remark);
            entity.setUpdatedAt(Instant.now());
        }
    }
}

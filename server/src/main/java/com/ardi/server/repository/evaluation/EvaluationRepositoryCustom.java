package com.ardi.server.repository.evaluation;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;

import java.util.List;

public interface EvaluationRepositoryCustom {
    List<EvaluationItemEntity> findByDynamicFilters(
        String name, long evaluationCategoryIdx
    );
}
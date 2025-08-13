package com.ardi.server.repository.evaluation;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationItemRepository extends JpaRepository<EvaluationItemEntity, Long>, EvaluationRepositoryCustom {

    List<EvaluationItemEntity> findAllByIdxIn(List<Long> evaluationItemIdxs);

    List<EvaluationItemEntity> findAllByIsUsed(boolean isUsed);
}
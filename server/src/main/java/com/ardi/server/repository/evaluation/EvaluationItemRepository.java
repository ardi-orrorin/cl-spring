package com.ardi.server.repository.evaluation;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EvaluationItemRepository extends JpaRepository<EvaluationItemEntity, Long> {

    List<EvaluationItemEntity> findAllByNameLikeAndEvaluationCategoryIdx(String name, long evaluationCategoryIdx);

    List<EvaluationItemEntity> findAllByIdxIn(List<Long> evaluationItemIdxs);
}
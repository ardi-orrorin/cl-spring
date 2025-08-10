package com.ardi.server.repository.evaluation;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationItemRepository extends JpaRepository<EvaluationItemEntity, Long> {
    
    List<EvaluationItemEntity> findByEvaluationCategoryIdx(Long categoryIdx);
    
    List<EvaluationItemEntity> findByIsUsedTrue();
    
    List<EvaluationItemEntity> findByIsUsedFalse();
    
    Optional<EvaluationItemEntity> findByName(String name);
    
    @Query("SELECT e FROM EvaluationItemEntity e WHERE e.evaluationCategory.idx = :categoryIdx AND e.isUsed = true")
    List<EvaluationItemEntity> findUsedItemsByCategory(@Param("categoryIdx") Long categoryIdx);
    
    @Query("SELECT e FROM EvaluationItemEntity e WHERE e.score >= :minScore AND e.score <= :maxScore")
    List<EvaluationItemEntity> findByScoreRange(@Param("minScore") int minScore, @Param("maxScore") int maxScore);
    
    boolean existsByName(String name);
}
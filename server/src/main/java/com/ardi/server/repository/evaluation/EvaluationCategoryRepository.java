package com.ardi.server.repository.evaluation;

import com.ardi.server.entity.evaluation.EvaluationCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EvaluationCategoryRepository extends JpaRepository<EvaluationCategoryEntity, Long> {
    
    Optional<EvaluationCategoryEntity> findByName(String name);
    
    List<EvaluationCategoryEntity> findAllByOrderByNameAsc();

    List<EvaluationCategoryEntity> findAllByIdxIn(List<Long> idxs);
    
    boolean existsByName(String name);
}
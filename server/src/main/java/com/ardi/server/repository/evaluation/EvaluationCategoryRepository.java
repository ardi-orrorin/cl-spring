package com.ardi.server.repository.evaluation;

import com.ardi.server.entity.evaluation.EvaluationCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EvaluationCategoryRepository extends JpaRepository<EvaluationCategoryEntity, Long> {
    
    boolean existsByName(String name);
}
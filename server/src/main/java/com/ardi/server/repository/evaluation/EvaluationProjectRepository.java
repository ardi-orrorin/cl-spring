package com.ardi.server.repository.evaluation;

import com.ardi.server.entity.evaluation.EvaluationProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationProjectRepository extends JpaRepository<EvaluationProjectEntity, Long> {
    
    Optional<EvaluationProjectEntity> findByName(String name);
    
    List<EvaluationProjectEntity> findAllByOrderByCreatedAtDesc();
    
    List<EvaluationProjectEntity> findByParticipationPeriodContaining(String period);
    
    boolean existsByName(String name);
}
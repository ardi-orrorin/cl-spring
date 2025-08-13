package com.ardi.server.repository.evaluation;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EvaluationItemRepositoryImpl implements EvaluationRepositoryCustom {
    
    private final EntityManager entityManager;

    @Override
    public List<EvaluationItemEntity> findByDynamicFilters(String name, long evaluationCategoryIdx) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EvaluationItemEntity> query = cb.createQuery(EvaluationItemEntity.class);
        Root<EvaluationItemEntity> root = query.from(EvaluationItemEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (evaluationCategoryIdx != 0) {
            predicates.add(cb.equal(root.get("evaluationCategory").get("idx"), evaluationCategoryIdx));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<EvaluationItemEntity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
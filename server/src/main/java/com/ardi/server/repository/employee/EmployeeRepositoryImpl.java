package com.ardi.server.repository.employee;

import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.dto.employee.WorkLocation;
import com.ardi.server.entity.employee.EmployeeEntity;
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
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    
    private final EntityManager entityManager;

    @Override
    public List<EmployeeEntity> findByDynamicFilters(String name, EmploymentStatus employmentStatus, WorkLocation workLocation) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (employmentStatus != null) {
            predicates.add(cb.equal(root.get("employmentStatus"), employmentStatus));
        }

        if (workLocation != null) {
            predicates.add(cb.equal(root.get("workLocation"), workLocation));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<EmployeeEntity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
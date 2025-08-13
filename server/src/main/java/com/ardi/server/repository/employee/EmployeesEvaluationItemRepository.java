package com.ardi.server.repository.employee;

import com.ardi.server.entity.employee.EmployeesEvaluationItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeesEvaluationItemRepository extends JpaRepository<EmployeesEvaluationItemEntity, Long> {
    List<EmployeesEvaluationItemEntity> findByEmployeesEvaluationIdx(Long employeesEvaluationIdx);
    
    @Modifying
    @Query("DELETE FROM EmployeesEvaluationItemEntity e WHERE e.employeesEvaluation.idx = :employeesEvaluationIdx")
    void deleteByEmployeesEvaluation_Idx(Long employeesEvaluationIdx);
}
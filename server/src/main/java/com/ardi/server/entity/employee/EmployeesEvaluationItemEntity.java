package com.ardi.server.entity.employee;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees_evaluation_items", 
       uniqueConstraints = @UniqueConstraint(
           name = "employees_evaluation_idx", 
           columnNames = {"employees_evaluation_idx", "evaluation_item_idx"}
       ))
@Getter
@Setter
@RequiredArgsConstructor
public class EmployeesEvaluationItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employees_evaluation_idx")
    private EmployeesEvaluationEntity employeesEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_item_idx")
    private EvaluationItemEntity evaluationItem;

}

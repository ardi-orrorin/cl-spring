package com.ardi.server.entity.employee;

import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees_evaluation_items")
@Getter
@Setter
@RequiredArgsConstructor
public class EmployeesEvaluationItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @OneToOne(fetch = FetchType.LAZY)
    private EmployeesEvaluationEntity employeesEvaluation;

    @OneToOne
    private EvaluationItemEntity evaluationItem;

}

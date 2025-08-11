package com.ardi.server.entity.evaluation;

import com.ardi.server.entity.employee.EmployeesEvaluationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "evaluation_projects")
@Getter
@Setter
@RequiredArgsConstructor
public class EvaluationProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idx;
    private String name;
    private String participationPeriod;
    private String remark;
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "employees_evaluation_idx")
    private EmployeesEvaluationEntity employeesEvaluation;
}

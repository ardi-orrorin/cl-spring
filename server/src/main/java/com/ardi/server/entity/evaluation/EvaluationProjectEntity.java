package com.ardi.server.entity.evaluation;

import com.ardi.server.dto.evaluation.response.ResponseEvaluationProject;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;
    private String name;
    private String participationPeriod;
    private String remark;
    private Instant createdAt;

    @OneToOne
    @JoinColumn(name = "employees_evaluation_idx")
    private EmployeesEvaluationEntity employeesEvaluation;


    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        if(name == null) name = "";
        if(participationPeriod == null) participationPeriod = "";
        if(remark == null) remark = "";
    }

    public ResponseEvaluationProject.Project toProject() {
        return new ResponseEvaluationProject.Project(
            idx,
            employeesEvaluation == null ? 0 : employeesEvaluation.getIdx(),
            name,
            participationPeriod,
            remark
        );
    }
}

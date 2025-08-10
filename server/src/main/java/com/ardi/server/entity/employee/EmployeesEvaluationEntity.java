package com.ardi.server.entity.employee;

import com.ardi.server.dto.employee.request.RequestEmployeeProject;
import com.ardi.server.entity.evaluation.EvaluationProjectEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "employees_evaluations")
@Getter
@Setter
public class EmployeesEvaluationEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idx;
    
    private int totalScore;
    private Long increaseRate;
    private long nextAnnualSalary;
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx")
    private EmployeeEntity employee;

    @OneToOne
    private EmployeesEvaluationItemEntity employeesEvaluationItem;

    @OneToMany(mappedBy = "employeesEvaluation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EvaluationProjectEntity> evaluationProjects;

    public void addEvaluationProject(EvaluationProjectEntity evaluationProject) {
        evaluationProjects.add(evaluationProject);
        evaluationProject.setEmployeesEvaluation(this);
    }

    public void updateEvaluationProject(RequestEmployeeProject.Create req) {
        evaluationProjects.stream()
            .filter(project -> project.getIdx() == req.idx())
            .findFirst()
            .ifPresent(req::update);
    }

}

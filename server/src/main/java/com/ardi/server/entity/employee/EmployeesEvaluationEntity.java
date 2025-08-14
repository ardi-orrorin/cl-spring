package com.ardi.server.entity.employee;

import com.ardi.server.dto.employee.response.ResponseEmployeeEvaluation;
import com.ardi.server.entity.evaluation.EvaluationProjectEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees_evaluations")
@Getter
@Setter
public class EmployeesEvaluationEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;
    
    private int totalScore;
    private int increaseRate;
    private long nextAnnualSalary;
    private int evaluationYear;
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx")
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "employeesEvaluation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EmployeesEvaluationItemEntity> employeesEvaluationItems;

    @OneToOne(mappedBy = "employeesEvaluation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private EvaluationProjectEntity evaluationProject;

    @PrePersist
    protected void init() {
        if(evaluationYear == 0) evaluationYear = LocalDate.now().getYear();
        if(createdAt == null) createdAt = Instant.now();
    }

    public static EmployeesEvaluationEntity create(EmployeeEntity employee) {
        EmployeesEvaluationEntity employeesEvaluationEntity = new EmployeesEvaluationEntity();
        employeesEvaluationEntity.setEmployee(employee);
        employeesEvaluationEntity.setEvaluationYear(LocalDate.now().getYear());
        employeesEvaluationEntity.setEmployeesEvaluationItems(List.of());
        return employeesEvaluationEntity;
    }

    public ResponseEmployeeEvaluation.List toList() {
        List<Long> evaluationItemsIdxs = employeesEvaluationItems
            .stream()
            .map(employeesEvaluationItemEntity ->
                employeesEvaluationItemEntity.getEvaluationItem().getIdx())
            .toList();

        return new ResponseEmployeeEvaluation.List(
            idx,
            employee.getIdx(),
            employee.getName(),
            employee.getHireYear(),
            employee.getEmployeeNumber(),
            employee.getCurrentAnnualSalary(),
            evaluationItemsIdxs,
            increaseRate,
            totalScore
        );
    }

    public ResponseEmployeeEvaluation.EmployeeEvaluation toResEmployeeEvaluation() {
        return new ResponseEmployeeEvaluation.EmployeeEvaluation(
            idx,
            employee.getName(),
            employee.getEmploymentStatus().getDisplayName(),
            employee.getCurrentAnnualSalary(),
            nextAnnualSalary,
            increaseRate,
            totalScore
        );
    }
}

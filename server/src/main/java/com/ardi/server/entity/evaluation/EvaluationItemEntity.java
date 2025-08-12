package com.ardi.server.entity.evaluation;

import com.ardi.server.dto.evaluation.response.ResponseEvaluationItem;
import com.ardi.server.entity.employee.EmployeesEvaluationItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.catalina.connector.Response;

import java.time.Instant;

@Entity
@Table(name = "evaluation_items")
@Getter
@Setter
@RequiredArgsConstructor
public class EvaluationItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;
    private String name;

    private int score;
    private boolean isUsed;
    private String remark;
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "evaluation_category_idx", insertable = false, updatable = false)
    private EvaluationCategoryEntity evaluationCategory;

    @OneToOne
    private EmployeesEvaluationItemEntity employeesEvaluationItem;

    public ResponseEvaluationItem.Summary toSummary() {
        return new ResponseEvaluationItem.Summary(
            idx,
            evaluationCategory.getName(),
            name,
            score,
            isUsed
        );
    }

    public ResponseEvaluationItem.Select toSelect() {
        return new ResponseEvaluationItem.Select(
            idx,
            name,
            score
        );
    }
}

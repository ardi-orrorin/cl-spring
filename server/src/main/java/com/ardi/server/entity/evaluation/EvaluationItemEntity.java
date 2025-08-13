package com.ardi.server.entity.evaluation;

import com.ardi.server.dto.evaluation.response.ResponseEvaluationItem;
import com.ardi.server.entity.employee.EmployeesEvaluationItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.catalina.connector.Response;

import java.time.Instant;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_category_idx")
    private EvaluationCategoryEntity evaluationCategory;

    @OneToMany(mappedBy = "evaluationItem", fetch = FetchType.LAZY)
    private List<EmployeesEvaluationItemEntity> employeesEvaluationItems;

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

    public ResponseEvaluationItem.Detail toDetail() {
        return new ResponseEvaluationItem.Detail(
            idx,
            name,
            score,
            isUsed,
            remark,
            evaluationCategory.getIdx()
        );
    }
}

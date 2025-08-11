package com.ardi.server.entity.evaluation;

import com.ardi.server.dto.evaluation.response.ResponseEvaluationCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "evaluation_categories")
@Getter
@Setter
@RequiredArgsConstructor
public class EvaluationCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idx;

    private String name;
    private Instant createdAt;

    @OneToMany(mappedBy = "evaluationCategory")
    private List<EvaluationItemEntity> evaluationItems;

    public ResponseEvaluationCategory.List toResList() {
        return new ResponseEvaluationCategory.List(
            idx,
            name
        );
    }
}

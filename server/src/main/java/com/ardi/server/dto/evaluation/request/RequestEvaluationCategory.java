package com.ardi.server.dto.evaluation.request;

import com.ardi.server.entity.evaluation.EvaluationCategoryEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class RequestEvaluationCategory {

    public record Create(
        @NotBlank(message = "카테고리 명은 필수 입력 값입니다.")
        String name
    ){
        public EvaluationCategoryEntity toEntity() {
            EvaluationCategoryEntity entity = new EvaluationCategoryEntity();
            entity.setName(name);
            entity.setCreatedAt(Instant.now());
            return entity;
        }
    }
}

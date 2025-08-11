package com.ardi.server.dto.employee.request;

import com.ardi.server.entity.evaluation.EvaluationProjectEntity;

import java.time.Instant;

public class RequestEmployeeProject {
    public record Create(
        long idx,
        String name,
        String participationPeriod,
        String remark
    ) {
        public EvaluationProjectEntity toEntity() {
            EvaluationProjectEntity entity = new EvaluationProjectEntity();
            entity.setName(name);
            entity.setParticipationPeriod(participationPeriod);
            entity.setRemark(remark);
            entity.setCreatedAt(Instant.now());
            return entity;
        }

        public void update(EvaluationProjectEntity entity) {
            if(!name.isBlank()) entity.setName(name);
            if(!participationPeriod.isBlank()) entity.setParticipationPeriod(participationPeriod);
            if(!remark.isBlank()) entity.setRemark(remark);
        }
    }
}

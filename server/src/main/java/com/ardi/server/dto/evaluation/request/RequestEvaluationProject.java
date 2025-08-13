package com.ardi.server.dto.evaluation.request;

import com.ardi.server.entity.evaluation.EvaluationProjectEntity;

import java.time.Instant;

public class RequestEvaluationProject {
    public record Create(
        long idx,
        long employeesEvaluationIdx,
        String name,
        String participationPeriod,
        String remark
    ) {
        public EvaluationProjectEntity toEntity() {
            EvaluationProjectEntity entity = new EvaluationProjectEntity();
            entity.setName(name);
            entity.setParticipationPeriod(participationPeriod);
            entity.setRemark(remark == null ? "" : remark);
            entity.setCreatedAt(Instant.now());
            return entity;
        }

        public void update(EvaluationProjectEntity entity) {
            if(name != null) entity.setName(name);
            if(participationPeriod != null) entity.setParticipationPeriod(participationPeriod);
            if(remark != null) entity.setRemark(remark);
        }
    }
}

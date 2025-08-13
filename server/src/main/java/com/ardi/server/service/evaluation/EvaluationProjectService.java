package com.ardi.server.service.evaluation;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.evaluation.request.RequestEvaluationProject;
import com.ardi.server.dto.evaluation.response.ResponseEvaluationProject;
import com.ardi.server.entity.employee.EmployeesEvaluationEntity;
import com.ardi.server.entity.evaluation.EvaluationProjectEntity;
import com.ardi.server.repository.employee.EmployeesEvaluationRepository;
import com.ardi.server.repository.evaluation.EvaluationProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluationProjectService {

    private final EvaluationProjectRepository evaluationProjectRepository;
    private final EmployeesEvaluationRepository employeesEvaluationRepository;

    public ResponseStatus<ResponseEvaluationProject.Project> findByEmployeesEvaluationIdx(long employeesEvaluationIdx) {
        EvaluationProjectEntity entity = evaluationProjectRepository.findByEmployeesEvaluationIdx(employeesEvaluationIdx)
            .orElse(new EvaluationProjectEntity());

        return ResponseStatus.success("success", entity.toProject());
    }

    public ResponseStatus<ResponseEvaluationProject.Project> findByIdx(long idx) {
        EvaluationProjectEntity entity = evaluationProjectRepository.findById(idx)
            .orElseThrow(() -> new IllegalArgumentException("EvaluationProject not found"));

        return ResponseStatus.success("success", entity.toProject());
    }

    @Transactional
    public ResponseStatus<Boolean> save(RequestEvaluationProject.Create req) {
        EvaluationProjectEntity entity = req.toEntity();
        EmployeesEvaluationEntity employeesEvaluationEntity = employeesEvaluationRepository.findById(req.employeesEvaluationIdx())
                .orElseThrow(() -> new IllegalArgumentException("EmployeesEvaluation not found"));

        entity.setEmployeesEvaluation(employeesEvaluationEntity);
        evaluationProjectRepository.save(entity);

        return ResponseStatus.successBoolean("success");
    }

    @Transactional
    public ResponseStatus<Boolean> update(RequestEvaluationProject.Create req) {
        EvaluationProjectEntity entity = evaluationProjectRepository.findById(req.idx())
            .orElseThrow(() -> new IllegalArgumentException("EvaluationProject not found"));

        req.update(entity);
        evaluationProjectRepository.save(entity);

        return ResponseStatus.successBoolean("success");
    }
}

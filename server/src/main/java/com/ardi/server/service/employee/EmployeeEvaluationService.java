package com.ardi.server.service.employee;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.employee.request.RequestEmployeeEvaluation;
import com.ardi.server.dto.employee.response.ResponseEmployeeEvaluation;
import com.ardi.server.entity.employee.EmployeesEvaluationEntity;
import com.ardi.server.entity.employee.EmployeesEvaluationItemEntity;
import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import com.ardi.server.repository.employee.EmployeesEvaluationItemRepository;
import com.ardi.server.repository.employee.EmployeesEvaluationRepository;
import com.ardi.server.repository.evaluation.EvaluationItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeEvaluationService {
    private final EvaluationItemRepository evaluationItemRepository;
    private final EmployeesEvaluationRepository employeesEvaluationRepository;
    private final EmployeesEvaluationItemRepository employeesEvaluationItemRepository;

    public ResponseStatus<List<ResponseEmployeeEvaluation.List>> findAll() {
        List<ResponseEmployeeEvaluation.List> data = employeesEvaluationRepository.findAll()
            .stream()
            .map(EmployeesEvaluationEntity::toList)
            .toList();

        return ResponseStatus.success("Employee evaluation list", data);
    }

    @Transactional
    public ResponseStatus<Boolean> update(RequestEmployeeEvaluation.Update req) {

        // Start 사원 평가 추가
        EmployeesEvaluationEntity employeesEvaluationEntity = employeesEvaluationRepository.findById(req.idx())
            .orElseThrow(() -> new IllegalArgumentException("Employee evaluation not found"));

        employeesEvaluationEntity.setIncreaseRate(req.increaseRate());

        List<EvaluationItemEntity> evaluationItemEntities = List.of();

        if (!req.evaluationItemIdxs().isEmpty()) {
            evaluationItemEntities = evaluationItemRepository.findAllByIdxIn(req.evaluationItemIdxs());
            int totalScore = evaluationItemEntities
                .stream()
                .reduce(0, (acc, evaluationItemEntity) ->
                    acc + evaluationItemEntity.getScore(), Integer::sum
                );
            employeesEvaluationEntity.setTotalScore(totalScore);
        }

        if(!req.projects().isEmpty()) {
            req.projects().forEach(project -> {
                if(project.idx() > 0 ) {
                    employeesEvaluationEntity.addEvaluationProject(project.toEntity());
                } else {
                    employeesEvaluationEntity.updateEvaluationProject(project);
                }
            });
        }

        employeesEvaluationRepository.save(employeesEvaluationEntity);
        // End 사원 평가 추가


        if(evaluationItemEntities.isEmpty()) {
            return ResponseStatus.successBoolean("Employee evaluation saved successfully");
        }

        // Start 평가 항목들 추가
        List<EmployeesEvaluationItemEntity> employeesEvaluationItemEntities = evaluationItemEntities.stream()
            .map(evaluationItemEntity -> {
                EmployeesEvaluationItemEntity employeesEvaluationItemEntity = new EmployeesEvaluationItemEntity();
                employeesEvaluationItemEntity.setEvaluationItem(evaluationItemEntity);
                employeesEvaluationItemEntity.setEmployeesEvaluation(employeesEvaluationEntity);
                return employeesEvaluationItemEntity;
            }).toList();

        employeesEvaluationItemRepository.saveAll(employeesEvaluationItemEntities);
        // End 평가 항목들 추가

        return ResponseStatus.successBoolean("Employee evaluation saved successfully");
    }


}

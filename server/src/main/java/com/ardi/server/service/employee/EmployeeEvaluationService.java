package com.ardi.server.service.employee;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.dto.employee.request.RequestEmployeeEvaluation;
import com.ardi.server.dto.employee.response.ResponseEmployeeEvaluation;
import com.ardi.server.entity.employee.EmployeeEntity;
import com.ardi.server.entity.employee.EmployeesEvaluationEntity;
import com.ardi.server.entity.employee.EmployeesEvaluationItemEntity;
import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import com.ardi.server.repository.employee.EmployeeRepository;
import com.ardi.server.repository.employee.EmployeesEvaluationItemRepository;
import com.ardi.server.repository.employee.EmployeesEvaluationRepository;
import com.ardi.server.repository.evaluation.EvaluationItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeEvaluationService {
    private final EvaluationItemRepository evaluationItemRepository;
    private final EmployeesEvaluationRepository employeesEvaluationRepository;
    private final EmployeesEvaluationItemRepository employeesEvaluationItemRepository;
    private final EmployeeRepository employeeRepository;

    public ResponseStatus<ResponseEmployeeEvaluation.Report> findAlUserReport() {
        List<EmployeesEvaluationEntity> entities = employeesEvaluationRepository
            .findAllByEvaluationYearAndEmployeeEmploymentStatusNot(LocalDate.now().getYear(), EmploymentStatus.RESIGNED);

        List<ResponseEmployeeEvaluation.EmployeeEvaluation> data = entities.stream()
            .map(EmployeesEvaluationEntity::toResEmployeeEvaluation)
            .toList();

        int resignCount = employeeRepository.countByEmploymentStatus(EmploymentStatus.RESIGNED);

        ResponseEmployeeEvaluation.Report report = new ResponseEmployeeEvaluation.Report(resignCount, data);

        return ResponseStatus.success("Employee evaluation list", report);    }

    public ResponseStatus<List<ResponseEmployeeEvaluation.List>> findAll() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAllByEmploymentStatusNot(EmploymentStatus.RESIGNED);

        List<EmployeesEvaluationEntity> currentYearEmployeesEvaluationEntities = employeeEntities.stream()
            .map(employeeEntity -> {
                if(employeeEntity.getEmployeesEvaluations().isEmpty()) {
                    return EmployeesEvaluationEntity.create(employeeEntity);
                } else {
                    return employeeEntity.getEmployeesEvaluations().stream()
                        .filter(employeesEvaluationEntity ->
                            employeesEvaluationEntity.getEvaluationYear() == LocalDate.now().getYear())
                        .findFirst()
                        .orElse(EmployeesEvaluationEntity.create(employeeEntity));
                }}
            ).toList();

        List<ResponseEmployeeEvaluation.List> data = currentYearEmployeesEvaluationEntities.stream()
            .map(EmployeesEvaluationEntity::toList)
            .toList();

        return ResponseStatus.success("Employee evaluation list", data);
    }

    @Transactional
    public ResponseStatus<Boolean> update(RequestEmployeeEvaluation.Update req) {
        if(req.idx() == 0) { // 새로운 평가 생성
            return save(req);
        }

        EmployeesEvaluationEntity employeesEvaluationEntity = employeesEvaluationRepository.findById(req.idx())
            .orElseThrow(() -> new IllegalArgumentException("Employee evaluation not found"));

        employeesEvaluationEntity.setIncreaseRate(req.increaseRate());

        List<EvaluationItemEntity> evaluationItemEntities = addEvaluationItemAndTotalScore(req, employeesEvaluationEntity);


        long nextAnnualSalary = calculateNextAnnualSalary(
            req.increaseRate(),
            employeesEvaluationEntity.getEmployee().getCurrentAnnualSalary()
        );

        employeesEvaluationEntity.setNextAnnualSalary(nextAnnualSalary);

        employeesEvaluationRepository.save(employeesEvaluationEntity);


        saveEmployeeEvaluationItemSave(evaluationItemEntities, employeesEvaluationEntity);

        return ResponseStatus.successBoolean("Employee evaluation saved successfully");
    }

    @Transactional
    public ResponseStatus<Boolean> save(RequestEmployeeEvaluation.Update req) {
        EmployeeEntity employeeEntity = employeeRepository.findById(req.employeeIdx())
            .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        EmployeesEvaluationEntity employeesEvaluationEntity = req.toEntity(employeeEntity);

        List<EvaluationItemEntity> evaluationItemEntities = addEvaluationItemAndTotalScore(req, employeesEvaluationEntity);


        long nextAnnualSalary = calculateNextAnnualSalary(
            req.increaseRate(),
            employeesEvaluationEntity.getEmployee().getCurrentAnnualSalary()
        );

        employeesEvaluationEntity.setNextAnnualSalary(nextAnnualSalary);

        employeesEvaluationRepository.save(employeesEvaluationEntity);

        saveEmployeeEvaluationItemSave(evaluationItemEntities, employeesEvaluationEntity);

        return ResponseStatus.successBoolean("Employee evaluation saved successfully");
    }

    private long calculateNextAnnualSalary(int increaseRate, long currentAnnualSalary) {
        return currentAnnualSalary + (currentAnnualSalary * increaseRate / 100);
    }

    private void saveEmployeeEvaluationItemSave(
        List<EvaluationItemEntity> evaluationItemEntities,
        EmployeesEvaluationEntity employeesEvaluationEntity
    ) {
        // 기존 평가 항목들 조회
        List<EmployeesEvaluationItemEntity> existingItems = 
            employeesEvaluationItemRepository.findByEmployeesEvaluationIdx(employeesEvaluationEntity.getIdx());
        
        // 기존 평가 항목 ID 목록
        List<Long> existingItemIds = existingItems.stream()
            .map(item -> item.getEvaluationItem().getIdx())
            .toList();
        
        // 새로 들어온 평가 항목 ID 목록
        List<Long> newItemIds = evaluationItemEntities.stream()
            .map(EvaluationItemEntity::getIdx)
            .toList();
        
        // 삭제할 항목들 (기존에 있지만 새로운 목록에 없는 것들)
        List<EmployeesEvaluationItemEntity> itemsToDelete = existingItems.stream()
            .filter(item -> !newItemIds.contains(item.getEvaluationItem().getIdx()))
            .toList();
        
        // 추가할 항목들 (새로운 목록에 있지만 기존에 없는 것들)
        List<EvaluationItemEntity> itemsToAdd = evaluationItemEntities.stream()
            .filter(item -> !existingItemIds.contains(item.getIdx()))
            .toList();
        
        // 삭제 실행
        if (!itemsToDelete.isEmpty()) {
            employeesEvaluationItemRepository.deleteAll(itemsToDelete);
        }
        
        // 추가 실행
        if (!itemsToAdd.isEmpty()) {
            List<EmployeesEvaluationItemEntity> newEmployeesEvaluationItemEntities = itemsToAdd.stream()
                .map(evaluationItemEntity -> {
                    EmployeesEvaluationItemEntity employeesEvaluationItemEntity = new EmployeesEvaluationItemEntity();
                    employeesEvaluationItemEntity.setEvaluationItem(evaluationItemEntity);
                    employeesEvaluationItemEntity.setEmployeesEvaluation(employeesEvaluationEntity);
                    return employeesEvaluationItemEntity;
                }).toList();
            
            employeesEvaluationItemRepository.saveAll(newEmployeesEvaluationItemEntities);
        }
    }

    private List<EvaluationItemEntity> addEvaluationItemAndTotalScore(RequestEmployeeEvaluation.Update req, EmployeesEvaluationEntity employeesEvaluationEntity) {
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
        return evaluationItemEntities;
    }
}

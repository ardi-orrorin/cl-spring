package com.ardi.server.service.evaluation;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.evaluation.request.RequestEvaluationCategory;
import com.ardi.server.dto.evaluation.response.ResponseEvaluationCategory;
import com.ardi.server.entity.evaluation.EvaluationCategoryEntity;
import com.ardi.server.repository.evaluation.EvaluationCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationCategoryService {
    private final EvaluationCategoryRepository evaluationCategoryRepository;

    public ResponseStatus<List<ResponseEvaluationCategory.List>> findAll() {
        List<ResponseEvaluationCategory.List> data =
            evaluationCategoryRepository.findAll()
                .stream()
                .map(EvaluationCategoryEntity::toResList)
                .toList();

        return ResponseStatus.success("Success", data);
    }

    @Transactional
    public ResponseStatus<Boolean> save(RequestEvaluationCategory.Create req) {

        boolean isExist = evaluationCategoryRepository.existsByName(req.name());

        if(isExist) {
            throw new IllegalArgumentException("이미 존재하는 평가 항목입니다.");
        }

        evaluationCategoryRepository.save(req.toEntity());

        return ResponseStatus.successBoolean("Success");
    }
}

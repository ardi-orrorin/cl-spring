package com.ardi.server.service.evaluation;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.evaluation.request.RequestEvaluationItem;
import com.ardi.server.dto.evaluation.response.ResponseEvaluationItem;
import com.ardi.server.entity.evaluation.EvaluationCategoryEntity;
import com.ardi.server.entity.evaluation.EvaluationItemEntity;
import com.ardi.server.repository.evaluation.EvaluationCategoryRepository;
import com.ardi.server.repository.evaluation.EvaluationItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationItemService {
    private final EvaluationItemRepository evaluationItemRepository;
    private final EvaluationCategoryRepository evaluationCategoryRepository;


    // 평가 항목 검색
    public ResponseStatus<List<ResponseEvaluationItem.Summary>> findAllByNameAndEvaluationCategoryIdx(
        RequestEvaluationItem.Search req
    ) {
        List<ResponseEvaluationItem.Summary> data =
            evaluationItemRepository.findAllByNameLikeAndEvaluationCategoryIdx(req.name(), req.evaluationCategoryIdx())
                .stream()
                .map(EvaluationItemEntity::toSummary)
                .toList();

        return ResponseStatus.success("Success", data);
    }

    public ResponseStatus<Boolean> save(RequestEvaluationItem.Create req) {
        EvaluationItemEntity entity = req.toEntity();
        EvaluationCategoryEntity evaluationCategoryEntity =
            evaluationCategoryRepository.findById(req.evaluationCategoryIdx())
                .orElseThrow(() -> new IllegalArgumentException("Evaluation Category not found"));

        entity.setEvaluationCategory(evaluationCategoryEntity);
        evaluationItemRepository.save(entity);

        return ResponseStatus.successBoolean("Created successfully");
    }

    public ResponseStatus<List<ResponseEvaluationItem.Select>> findAllByUsed() {
        List<ResponseEvaluationItem.Select> data =
            evaluationItemRepository.findAllByIsUsed(true)
                .stream()
                .map(EvaluationItemEntity::toSelect)
                .toList();

        return ResponseStatus.success("Success", data);
    }
}

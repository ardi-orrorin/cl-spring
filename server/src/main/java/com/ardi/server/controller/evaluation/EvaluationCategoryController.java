package com.ardi.server.controller.evaluation;


import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.evaluation.request.RequestEvaluationCategory;
import com.ardi.server.dto.evaluation.response.ResponseEvaluationCategory;
import com.ardi.server.service.evaluation.EvaluationCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluation-categories")
@RequiredArgsConstructor
public class EvaluationCategoryController {
    private final EvaluationCategoryService evaluationCategoryService;

    @GetMapping("")
    public ResponseEntity<ResponseStatus<List<ResponseEvaluationCategory.List>>> findAll() {
        ResponseStatus<List<ResponseEvaluationCategory.List>> result =
            evaluationCategoryService.findAll();

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<ResponseStatus<Boolean>> save(
        @Valid @RequestBody RequestEvaluationCategory.Create req
    ) {
        ResponseStatus<Boolean> result =
            evaluationCategoryService.save(req);

        return ResponseEntity.ok(result);
    }
}

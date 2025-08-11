package com.ardi.server.controller.evaluation;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.evaluation.request.RequestEvaluationItem;
import com.ardi.server.dto.evaluation.response.ResponseEvaluationItem;
import com.ardi.server.service.evaluation.EvaluationItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluation-items")
@RequiredArgsConstructor
public class EvaluationItemController {

    private final EvaluationItemService evaluationItemService;

    @GetMapping("")
    public ResponseEntity<ResponseStatus<List<ResponseEvaluationItem.Summary>>> findAllByNameAndEvaluationCategoryIdx(
        RequestEvaluationItem.Search req
    ) {
        ResponseStatus<List<ResponseEvaluationItem.Summary>> result =
            evaluationItemService.findAllByNameAndEvaluationCategoryIdx(req);

        return ResponseEntity.ok(result);
    }

    @GetMapping("select")
    public ResponseEntity<ResponseStatus<List<ResponseEvaluationItem.Select>>> findAllByUsed() {
        ResponseStatus<List<ResponseEvaluationItem.Select>> result =
            evaluationItemService.findAllByUsed();

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<ResponseStatus<Boolean>> save(
        @Valid @RequestBody RequestEvaluationItem.Create req
    ) {
        ResponseStatus<Boolean> result =
            evaluationItemService.save(req);

        return ResponseEntity.ok(result);
    }
}

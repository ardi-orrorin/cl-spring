package com.ardi.server.controller.evaluation;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.evaluation.request.RequestEvaluationProject;
import com.ardi.server.dto.evaluation.response.ResponseEvaluationProject;
import com.ardi.server.service.evaluation.EvaluationProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/evaluation-projects")
public class EvaluationProjectController {
    private final EvaluationProjectService evaluationProjectService;

    @GetMapping("/{idx}")
    public ResponseEntity<ResponseStatus<ResponseEvaluationProject.Project>> findById(
        @PathVariable long idx
    ) {
        ResponseStatus<ResponseEvaluationProject.Project> result =
            evaluationProjectService.findByIdx(idx);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/employees/{employeeIdx}")
    public ResponseEntity<ResponseStatus<ResponseEvaluationProject.Project>> findByEmployeeIdx(
        @PathVariable long employeeIdx
    ) {
        ResponseStatus<ResponseEvaluationProject.Project> result =
            evaluationProjectService.findByEmployeesEvaluationIdx(employeeIdx);

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<ResponseStatus<Boolean>> create(
        @Valid @RequestBody RequestEvaluationProject.Create req
    ) {
        ResponseStatus<Boolean> result = evaluationProjectService.save(req);
        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity<ResponseStatus<Boolean>> update(
        @Valid @RequestBody RequestEvaluationProject.Create req
    ) {
        ResponseStatus<Boolean> result = evaluationProjectService.update(req);
        return ResponseEntity.ok(result);
    }
}

package com.ardi.server.controller.employee;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.employee.request.RequestEmployeeEvaluation;
import com.ardi.server.dto.employee.response.ResponseEmployeeEvaluation;
import com.ardi.server.service.employee.EmployeeEvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees-evaluation")
@RequiredArgsConstructor
public class EmployeeEvaluationController {
    private final EmployeeEvaluationService employeeEvaluationService;

    @GetMapping("")
    public ResponseEntity<ResponseStatus<List<ResponseEmployeeEvaluation.List>>> findAll() {
        ResponseStatus<List<ResponseEmployeeEvaluation.List>> result =
            employeeEvaluationService.findAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/report")
    public ResponseEntity<ResponseStatus<List<ResponseEmployeeEvaluation.Report>>> findAllReport() {
        ResponseStatus<List<ResponseEmployeeEvaluation.Report>> result =
            employeeEvaluationService.findAlUserReport();

        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity<ResponseStatus<Boolean>> update(
        @Valid @RequestBody RequestEmployeeEvaluation.Update req
    ) {
        ResponseStatus<Boolean> result =
            employeeEvaluationService.update(req);

        return ResponseEntity.ok(result);
    }
}

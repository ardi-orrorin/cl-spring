package com.ardi.server.controller.employee;


import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.employee.request.RequestEmployee;
import com.ardi.server.dto.employee.response.ResponseEmployee;
import com.ardi.server.dto.employee.response.ResponseEmployeeDetail;
import com.ardi.server.service.employee.EmployeeDetailService;
import com.ardi.server.service.employee.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeDetailService employeeDetailService;

    @GetMapping("")
    public ResponseEntity<ResponseStatus<List<ResponseEmployee.Summary>>> findAll(
        RequestEmployee.Search req
    ) {
        ResponseStatus<List<ResponseEmployee.Summary>> result =
            employeeService.findAllByNameAndEmploymentStatusAndWorkLocation(req);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{idx}")
    public ResponseEntity<ResponseStatus<ResponseEmployee.Detail>> findByIdx(
        @PathVariable long idx
    ) {
        ResponseStatus<ResponseEmployee.Detail> result =
            employeeService.findByIdx(idx);

        return ResponseEntity.ok(result);
    }

    @GetMapping("status")
    public ResponseEntity<ResponseStatus<Map<String, List<String>>>> findAllStatus() {
        ResponseStatus<Map<String, List<String>>> result =
            employeeService.findEmployeeStatus();

        return ResponseEntity.ok(result);
    }

    @GetMapping("detail/{employeeIdx}")
    public ResponseEntity<ResponseStatus<ResponseEmployeeDetail.Detail>> findByEmployeeIdx(
        @PathVariable long employeeIdx
    ) {
        ResponseStatus<ResponseEmployeeDetail.Detail> result =
            employeeDetailService.findByEmployeeIdx(employeeIdx);

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<ResponseStatus<Boolean>> save(
        @Valid @RequestBody RequestEmployee.Create req
    ) {
        ResponseStatus<Boolean> result
            = employeeService.save(req);

        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity<ResponseStatus<Boolean>> update(
        @Valid @RequestBody RequestEmployee.Create req
    ) {
        ResponseStatus<Boolean> result
            = employeeService.update(req);

        return ResponseEntity.ok(result);
    }
}

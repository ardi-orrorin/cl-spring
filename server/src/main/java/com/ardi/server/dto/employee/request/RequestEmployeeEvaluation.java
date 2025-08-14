package com.ardi.server.dto.employee.request;

import com.ardi.server.entity.employee.EmployeeEntity;
import com.ardi.server.entity.employee.EmployeesEvaluationEntity;

import java.util.List;

public class RequestEmployeeEvaluation {
    public record Update(
        long idx,
        long employeeIdx,
        List<Long> evaluationItemIdxs,
        int increaseRate
    ) {
        public EmployeesEvaluationEntity toEntity(EmployeeEntity employee) {
            EmployeesEvaluationEntity employeesEvaluationEntity = new EmployeesEvaluationEntity();
            employeesEvaluationEntity.setIncreaseRate(this.increaseRate);
            employeesEvaluationEntity.setEmployee(employee);
            return employeesEvaluationEntity;
        }
    }
}

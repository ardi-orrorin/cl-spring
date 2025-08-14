package com.ardi.server.repository.employee;

import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.entity.employee.EmployeesEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeesEvaluationRepository extends JpaRepository<EmployeesEvaluationEntity, Long> {

    List<EmployeesEvaluationEntity> findAllByEvaluationYearAndEmployeeEmploymentStatusNot(int year, EmploymentStatus employmentStatus);
}
package com.ardi.server.repository.employee;

import com.ardi.server.entity.employee.EmployeesEvaluationItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeesEvaluationItemRepository extends JpaRepository<EmployeesEvaluationItemEntity, Long> {
}
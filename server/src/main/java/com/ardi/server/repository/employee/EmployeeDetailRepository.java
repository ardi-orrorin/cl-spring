package com.ardi.server.repository.employee;

import com.ardi.server.entity.employee.EmployeeDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetailEntity, Long> {

    Optional<EmployeeDetailEntity> findByEmployeeIdx(long idx);
}
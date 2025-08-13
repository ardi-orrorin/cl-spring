package com.ardi.server.repository.employee;

import com.ardi.server.entity.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, EmployeeRepositoryCustom {

    @Query("SELECT MAX(CAST(e.employeeNumber AS int)) FROM EmployeeEntity e WHERE e.hireYear = :hireYear")
    Optional<String> findLastEmployeeNumber(String hireYear);
}

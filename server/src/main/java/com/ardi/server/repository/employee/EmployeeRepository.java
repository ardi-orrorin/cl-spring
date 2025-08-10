package com.ardi.server.repository.employee;

import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.dto.employee.WorkLocation;
import com.ardi.server.entity.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findAllByNameAndEmploymentStatusAndWorkLocation(
        String name, EmploymentStatus employmentStatus, WorkLocation workLocation
    );

    @Query("SELECT MAX(CAST(e.employeeNumber AS int)) FROM EmployeeEntity e WHERE e.hireYear = :hireYear")
    String findLastEmployeeNumber(String hireYear);
}

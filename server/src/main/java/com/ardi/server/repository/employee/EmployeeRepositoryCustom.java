package com.ardi.server.repository.employee;

import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.dto.employee.WorkLocation;
import com.ardi.server.entity.employee.EmployeeEntity;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<EmployeeEntity> findByDynamicFilters(String name, EmploymentStatus employmentStatus, WorkLocation workLocation);
}
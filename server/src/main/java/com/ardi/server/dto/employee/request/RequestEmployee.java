package com.ardi.server.dto.employee.request;

import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.dto.employee.JobTitle;
import com.ardi.server.dto.employee.WorkLocation;
import com.ardi.server.entity.employee.EmployeeDetailEntity;
import com.ardi.server.entity.employee.EmployeeEntity;
import com.ardi.server.entity.employee.EmployeesEvaluationEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class RequestEmployee {

    public record Search (
        String name,
        String employmentStatus,
        String workLocation
    ){}

    public record Create (
        long idx,

        @NotBlank(message = "Name is required")
        String name,

        String employeeNumber,

        @NotBlank(message = "Hire year is required")
        String hireYear,

        @NotBlank(message = "Job title is required")
        String jobTitle,

        long currentAnnualSalary,

        @NotBlank(message = "Employment status is required")
        String employmentStatus,

        @NotBlank(message = "Department name is required")
        String deptName,

        @NotBlank(message = "Work location is required")
        String workLocation,

        String email,
        String phoneNumber,
        String remark
    ){
        public EmployeeEntity toEntity() {
            EmployeeEntity entity = new EmployeeEntity();

            updateEntity(entity);

            entity.setCreatedAt(Instant.now());

            return entity;
        }

        public EmployeeEntity updateEntity(EmployeeEntity entity) {
            entity.setName(name);
            entity.setEmployeeNumber(employeeNumber);
            entity.setHireYear(hireYear);
            entity.setJobTitle(JobTitle.fromDisplayName(jobTitle));
            entity.setCurrentAnnualSalary(currentAnnualSalary);
            entity.setEmploymentStatus(EmploymentStatus.fromDisplayName(employmentStatus));
            entity.setWorkLocation(WorkLocation.fromDisplayName(workLocation));
            entity.setDeptName(deptName);
            entity.setUpdatedAt(Instant.now());

            return entity;
        }


        public EmployeeDetailEntity toDetailEntity(EmployeeEntity employee) {
            EmployeeDetailEntity detailEntity = new EmployeeDetailEntity();
            updateDetailEntity(detailEntity);
            detailEntity.setEmployee(employee);

            return detailEntity;
        }

        public EmployeeDetailEntity updateDetailEntity(EmployeeDetailEntity detailEntity) {
            detailEntity.setEmail(email);
            detailEntity.setPhoneNumber(phoneNumber);
            detailEntity.setRemark(remark);
            return detailEntity;
        }

        public EmployeesEvaluationEntity toEvaluationEntity(EmployeeEntity employee) {
            EmployeesEvaluationEntity evaluationEntity = new EmployeesEvaluationEntity();
            evaluationEntity.setEmployee(employee);
            evaluationEntity.setCreatedAt(Instant.now());
            evaluationEntity.setTotalScore(0);
            return evaluationEntity;
        }
    }
}

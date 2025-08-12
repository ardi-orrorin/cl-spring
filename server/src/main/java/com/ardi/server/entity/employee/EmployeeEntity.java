package com.ardi.server.entity.employee;

import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.dto.employee.JobTitle;
import com.ardi.server.dto.employee.response.ResponseEmployee;
import com.ardi.server.dto.employee.WorkLocation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@RequiredArgsConstructor
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;
    private String name;
    private String employeeNumber;
    private String hireYear;

    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;
    private long currentAnnualSalary;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Enumerated(EnumType.STRING)
    private WorkLocation workLocation;

    private String deptName;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeDetailEntity employeeDetail;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EmployeesEvaluationEntity> employeesEvaluations;

    public ResponseEmployee.Summary toSummary() {
        return new ResponseEmployee.Summary(
            this.idx,
            this.name,
            this.employeeNumber,
            this.hireYear,
            this.jobTitle.getDisplayName(),
            this.currentAnnualSalary,
            this.employmentStatus.getDisplayName(),
            this.workLocation.getDisplayName()
        );
    }
}

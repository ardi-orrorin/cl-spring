package com.ardi.server.service.employee;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.employee.EmploymentStatus;
import com.ardi.server.dto.employee.JobTitle;
import com.ardi.server.dto.employee.request.RequestEmployee;
import com.ardi.server.dto.employee.response.ResponseEmployee;
import com.ardi.server.dto.employee.WorkLocation;
import com.ardi.server.entity.employee.EmployeeDetailEntity;
import com.ardi.server.entity.employee.EmployeeEntity;
import com.ardi.server.entity.employee.EmployeesEvaluationEntity;
import com.ardi.server.repository.employee.EmployeeDetailRepository;
import com.ardi.server.repository.employee.EmployeeRepository;
import com.ardi.server.repository.employee.EmployeesEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeDetailRepository employeeDetailRepository;
    private final EmployeesEvaluationRepository employeesEvaluationRepository;
    private final static List<String> JOB_TITLES = JobTitle.getAllJobTitles();
    private final static List<String> EMPLOYMENT_STATUSES = EmploymentStatus.getAllEmploymentStatuses();
    private final static List<String> WORK_LOCATIONS = WorkLocation.getAllWorkLocations();

    // 사원 목록 검색
    public ResponseStatus<List<ResponseEmployee.Summary>> findAllByNameAndEmploymentStatusAndWorkLocation(RequestEmployee.Search req) {
        List<ResponseEmployee.Summary> data =
                employeeRepository.findAllByNameAndEmploymentStatusAndWorkLocation(
                   req.name(),
                    EmploymentStatus.fromDisplayName(req.employmentStatus()),
                    WorkLocation.fromDisplayName(req.workLocation())
                ).stream()
                .map(EmployeeEntity::toSummary)
                .toList();

        return ResponseStatus.success("Success",  data);
    }

    // 사원 등록시 사용할 사원 상태 정보 조회
    @Cacheable(value = "employeeStatus")
    public ResponseStatus<Map<String, List<String>>> findEmployeeStatus() {
        Map<String, List<String>> data = Map.of(
            "jobTitles", JOB_TITLES,
            "employmentStatuses", EMPLOYMENT_STATUSES,
            "workLocations", WORK_LOCATIONS
        );

        return ResponseStatus.success("Success", data);
    }

    @Transactional
    public ResponseStatus<Boolean> save(RequestEmployee.Create req) {
        EmployeeEntity entity = req.toEntity();

        // 사번 자동 생성
        if(entity.getEmployeeNumber().isBlank()) {
            String currentLastEmployeeNumber =
                employeeRepository.findLastEmployeeNumber(entity.getHireYear());

            int number = Integer.parseInt(currentLastEmployeeNumber.substring(4)) + 1;
            String nextEmployeeNumber = entity.getHireYear()
                + String.format("%03d", number);

            entity.setEmployeeNumber(nextEmployeeNumber);
        }

        employeeRepository.save(entity);

        EmployeeDetailEntity detailEntity = req.toDetailEntity(entity);
        employeeDetailRepository.save(detailEntity);

        EmployeesEvaluationEntity evaluationEntity = req.toEvaluationEntity(entity);
        employeesEvaluationRepository.save(evaluationEntity);

        return ResponseStatus.successBoolean("Created successfully");
    }
}

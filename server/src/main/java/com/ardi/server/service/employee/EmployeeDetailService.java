package com.ardi.server.service.employee;

import com.ardi.server.dto.common.ResponseStatus;
import com.ardi.server.dto.employee.response.ResponseEmployeeDetail;
import com.ardi.server.entity.employee.EmployeeDetailEntity;
import com.ardi.server.repository.employee.EmployeeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeDetailService {
    private final EmployeeDetailRepository employeeDetailRepository;

    public ResponseStatus<ResponseEmployeeDetail.Detail> findByEmployeeIdx(long employeeIdx) {
        EmployeeDetailEntity employeeDetailEntity =
            employeeDetailRepository.findByEmployeeIdx(employeeIdx)
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        return ResponseStatus.success("Success", employeeDetailEntity.toDetail());
    }

}

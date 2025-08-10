package com.ardi.server.entity.employee;

import com.ardi.server.dto.employee.response.ResponseEmployeeDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_details")
@Getter
@Setter
@RequiredArgsConstructor
public class EmployeeDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idx;

    private String email;
    private String phoneNumber;
    private String remark;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx")
    private EmployeeEntity employee;

    public ResponseEmployeeDetail.Detail toDetail() {
        return new ResponseEmployeeDetail.Detail(
            this.email,
            this.phoneNumber,
            this.remark
        );
    }
}

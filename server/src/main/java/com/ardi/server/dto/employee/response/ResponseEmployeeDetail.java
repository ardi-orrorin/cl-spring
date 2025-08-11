package com.ardi.server.dto.employee.response;

public class ResponseEmployeeDetail {

    public record Detail(
        String email,
        String phoneNumber,
        String remark
    ){}
}

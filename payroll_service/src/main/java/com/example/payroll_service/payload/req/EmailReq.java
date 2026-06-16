package com.example.payroll_service.payload.req;

import lombok.Data;

@Data
public class EmailReq {
    private String to;
    private String subject;
    private String body;
}
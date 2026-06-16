package com.example.employee_service.service;

import com.example.employee_service.payload.req.EmailReq;
import com.example.employee_service.payload.res.GlobalRes;

public interface EmailService {
    GlobalRes<String> sendEmail(EmailReq req);
}
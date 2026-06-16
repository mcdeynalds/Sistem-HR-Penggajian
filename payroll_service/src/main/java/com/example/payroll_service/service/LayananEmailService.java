package com.example.payroll_service.service;

import com.example.payroll_service.payload.req.EmailReq;
import com.example.payroll_service.payload.res.GlobalRes;

public interface LayananEmailService {
    GlobalRes<String> kirimEmail(EmailReq req);
}
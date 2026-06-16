package com.example.employee_service.controller;

import com.example.employee_service.payload.req.EmailReq;
import com.example.employee_service.payload.res.GlobalRes;
import com.example.employee_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<String>> sendEmail(@RequestBody EmailReq req) {
        return ResponseEntity.ok(emailService.sendEmail(req));
    }
}
package com.example.payroll_service.controller;

import com.example.payroll_service.payload.req.EmailReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.service.LayananEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private LayananEmailService layananEmailService;

    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<String>> kirimEmail(
            @RequestBody EmailReq req) {
        return ResponseEntity.ok(layananEmailService.kirimEmail(req));
    }
}
package com.example.employee_service.controller;

import com.example.employee_service.payload.req.LoginReq;
import com.example.employee_service.payload.req.RegisterReq;
import com.example.employee_service.payload.res.GlobalRes;
import com.example.employee_service.payload.res.LoginRes;
import com.example.employee_service.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Stateless - JWT
    @PostMapping("/loginJwt")
    public ResponseEntity<GlobalRes<LoginRes>> loginJwt(@RequestBody LoginReq req) {
        return ResponseEntity.ok(authService.loginJwt(req));
    }

    // Stateful - Session
    @PostMapping("/login")
    public ResponseEntity<GlobalRes<LoginRes>> loginSession(
            @RequestBody LoginReq req,
            HttpServletRequest request) {
        return ResponseEntity.ok(authService.loginSession(req, request));
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<GlobalRes<String>> logout(
            HttpServletRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(authService.logout(request, response));
    }

    // Register
    @PostMapping("/register")
    public ResponseEntity<GlobalRes<String>> register(@RequestBody RegisterReq req) {
        return ResponseEntity.ok(authService.register(req));
    }
}
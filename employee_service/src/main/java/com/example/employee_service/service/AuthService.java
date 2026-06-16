package com.example.employee_service.service;

import com.example.employee_service.payload.req.LoginReq;
import com.example.employee_service.payload.req.RegisterReq;
import com.example.employee_service.payload.res.GlobalRes;
import com.example.employee_service.payload.res.LoginRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    GlobalRes<LoginRes> loginJwt(LoginReq req);
    GlobalRes<LoginRes> loginSession(LoginReq req, HttpServletRequest request);
    GlobalRes<String> logout(HttpServletRequest request, HttpServletResponse response);
    GlobalRes<String> register(RegisterReq req);
}
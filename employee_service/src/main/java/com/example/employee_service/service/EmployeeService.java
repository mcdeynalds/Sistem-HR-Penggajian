package com.example.employee_service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.employee_service.payload.req.EmployeeReq;
import com.example.employee_service.payload.res.EmployeeRes;
import com.example.employee_service.payload.res.GlobalRes;

public interface EmployeeService {
    GlobalRes<List<EmployeeRes>> getAll();
    GlobalRes<EmployeeRes> getById(Long id);
    GlobalRes<EmployeeRes> create(EmployeeReq req);
    GlobalRes<EmployeeRes> update(Long id, EmployeeReq req);
    GlobalRes<String> delete(Long id);
    GlobalRes<String> importFromExcel(MultipartFile file);
}
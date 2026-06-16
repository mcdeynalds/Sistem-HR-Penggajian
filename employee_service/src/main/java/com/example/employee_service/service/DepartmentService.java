package com.example.employee_service.service;

import com.example.employee_service.payload.res.GlobalRes;

import java.util.List;

import com.example.employee_service.payload.req.DepartmentReq;
import com.example.employee_service.payload.res.DepartmentRes;

public interface DepartmentService {
    GlobalRes<List<DepartmentRes>> getAll();

    GlobalRes<DepartmentRes> getById(Long id);

    GlobalRes<DepartmentRes> create(DepartmentReq req);

    GlobalRes<DepartmentRes> update(Long id, DepartmentReq req);

    GlobalRes<String> delete(Long id);
}
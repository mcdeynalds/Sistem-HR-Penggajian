package com.example.employee_service.service.impl;

import com.example.employee_service.entity.DepartmentEntity;
import com.example.employee_service.payload.req.DepartmentReq;
import com.example.employee_service.payload.res.DepartmentRes;
import com.example.employee_service.payload.res.GlobalRes;
import com.example.employee_service.repository.DepartmentRepository;
import com.example.employee_service.service.DepartmentService;
import com.example.employee_service.utility.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public GlobalRes<List<DepartmentRes>> getAll() {
        try {
            List<DepartmentRes> list = departmentRepository.findAll()
                    .stream()
                    .map(this::toRes)
                    .collect(Collectors.toList());
            return new GlobalRes<>(true, Message.DEPARTMENT_FOUND, list);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<DepartmentRes> getById(Long id) {
        try {
            DepartmentEntity dept = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.DEPARTMENT_NOT_FOUND));
            return new GlobalRes<>(true, Message.DEPARTMENT_FOUND, toRes(dept));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<DepartmentRes> create(DepartmentReq req) {
        try {
            if (departmentRepository.existsByDepartmentName(req.getDepartmentName())) {
                return new GlobalRes<>(false, Message.DEPARTMENT_EXISTS, null);
            }
            DepartmentEntity dept = new DepartmentEntity();
            dept.setDepartmentName(req.getDepartmentName());
            dept.setDescription(req.getDescription());

            departmentRepository.save(dept);

            return new GlobalRes<>(true, Message.DEPARTMENT_CREATED, toRes(dept));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<DepartmentRes> update(Long id, DepartmentReq req) {
        try {
            DepartmentEntity dept = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.DEPARTMENT_NOT_FOUND));
            dept.setDepartmentName(req.getDepartmentName());
            dept.setDescription(req.getDescription());

            departmentRepository.save(dept);

            return new GlobalRes<>(true, Message.DEPARTMENT_UPDATED, toRes(dept));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<String> delete(Long id) {
        try {
            DepartmentEntity dept = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.DEPARTMENT_NOT_FOUND));

            departmentRepository.delete(dept);

            return new GlobalRes<>(true, Message.DEPARTMENT_DELETED, null);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    // Helper: convert entity ke response
    private DepartmentRes toRes(DepartmentEntity dept) {
        DepartmentRes res = new DepartmentRes();
        res.setId(dept.getId());
        res.setDepartmentName(dept.getDepartmentName());
        res.setDescription(dept.getDescription());
        return res;
    }
}
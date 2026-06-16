package com.example.employee_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_service.payload.req.DepartmentReq;
import com.example.employee_service.payload.res.DepartmentRes;
import com.example.employee_service.payload.res.GlobalRes;
import com.example.employee_service.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<List<DepartmentRes>>> getAll() {
        return ResponseEntity.ok(departmentService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<DepartmentRes>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalRes<DepartmentRes>> create(
            @RequestBody DepartmentReq req) {
        return ResponseEntity.ok(departmentService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalRes<DepartmentRes>> update(
            @PathVariable Long id,
            @RequestBody DepartmentReq req) {
        return ResponseEntity.ok(departmentService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalRes<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.delete(id));
    }
}
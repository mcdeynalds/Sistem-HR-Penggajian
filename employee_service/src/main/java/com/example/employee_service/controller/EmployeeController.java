package com.example.employee_service.controller;

import com.example.employee_service.payload.req.EmployeeReq;
import com.example.employee_service.payload.res.EmployeeRes;
import com.example.employee_service.payload.res.GlobalRes;
import com.example.employee_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<List<EmployeeRes>>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD', 'KARYAWAN')")
    public ResponseEntity<GlobalRes<EmployeeRes>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<EmployeeRes>> create(
            @RequestBody EmployeeReq req) {
        return ResponseEntity.ok(employeeService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<EmployeeRes>> update(
            @PathVariable Long id,
            @RequestBody EmployeeReq req) {
        return ResponseEntity.ok(employeeService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalRes<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.delete(id));
    }

    // Import data karyawan dari Excel
    @PostMapping("/import")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<String>> importExcel(
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(employeeService.importFromExcel(file));
    }
}
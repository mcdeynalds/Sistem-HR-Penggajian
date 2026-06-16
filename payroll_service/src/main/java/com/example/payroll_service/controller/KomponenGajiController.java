package com.example.payroll_service.controller;

import com.example.payroll_service.payload.req.KomponenGajiReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.payload.res.KomponenGajiRes;
import com.example.payroll_service.service.KomponenGajiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/komponen-gaji")
public class KomponenGajiController {

    @Autowired
    private KomponenGajiService komponenGajiService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<List<KomponenGajiRes>>> ambilSemua() {
        return ResponseEntity.ok(komponenGajiService.ambilSemua());
    }

    @GetMapping("/karyawan/{karyawanId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<KomponenGajiRes>> ambilByKaryawanId(
            @PathVariable Long karyawanId) {
        return ResponseEntity.ok(komponenGajiService.ambilByKaryawanId(karyawanId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalRes<KomponenGajiRes>> tambah(
            @RequestBody KomponenGajiReq req) {
        return ResponseEntity.ok(komponenGajiService.tambah(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalRes<KomponenGajiRes>> update(
            @PathVariable Long id,
            @RequestBody KomponenGajiReq req) {
        return ResponseEntity.ok(komponenGajiService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalRes<String>> hapus(@PathVariable Long id) {
        return ResponseEntity.ok(komponenGajiService.hapus(id));
    }
}
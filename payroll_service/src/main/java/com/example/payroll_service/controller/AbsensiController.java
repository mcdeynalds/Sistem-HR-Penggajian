package com.example.payroll_service.controller;

import com.example.payroll_service.payload.req.RingkasanAbsensiReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.payload.res.RingkasanAbsensiRes;
import com.example.payroll_service.service.AbsensiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/absensi")
public class AbsensiController {

    @Autowired
    private AbsensiService absensiService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<List<RingkasanAbsensiRes>>> ambilSemua() {
        return ResponseEntity.ok(absensiService.ambilSemua());
    }

    @GetMapping("/karyawan/{karyawanId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<RingkasanAbsensiRes>> ambilByKaryawanDanBulan(
            @PathVariable Long karyawanId,
            @RequestParam Integer bulan,
            @RequestParam Integer tahun) {
        return ResponseEntity.ok(
                absensiService.ambilByKaryawanDanBulan(karyawanId, bulan, tahun));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<RingkasanAbsensiRes>> tambah(
            @RequestBody RingkasanAbsensiReq req) {
        return ResponseEntity.ok(absensiService.tambah(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<RingkasanAbsensiRes>> update(
            @PathVariable Long id,
            @RequestBody RingkasanAbsensiReq req) {
        return ResponseEntity.ok(absensiService.update(id, req));
    }
}
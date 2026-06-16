package com.example.payroll_service.controller;

import com.example.payroll_service.payload.req.GeneratePenggajianReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.payload.res.PenggajianRes;
import com.example.payroll_service.service.PenggajianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/penggajian")
public class PenggajianController {

    @Autowired
    private PenggajianService penggajianService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<List<PenggajianRes>>> ambilSemua() {
        return ResponseEntity.ok(penggajianService.ambilSemua());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD', 'KARYAWAN')")
    public ResponseEntity<GlobalRes<PenggajianRes>> ambilById(
            @PathVariable Long id) {
        return ResponseEntity.ok(penggajianService.ambilById(id));
    }

    @GetMapping("/karyawan/{karyawanId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD', 'KARYAWAN')")
    public ResponseEntity<GlobalRes<List<PenggajianRes>>> ambilByKaryawanId(
            @PathVariable Long karyawanId) {
        return ResponseEntity.ok(penggajianService.ambilByKaryawanId(karyawanId));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<List<PenggajianRes>>> ambilByBulanDanTahun(
            @RequestParam Integer bulan,
            @RequestParam Integer tahun) {
        return ResponseEntity.ok(
                penggajianService.ambilByBulanDanTahun(bulan, tahun));
    }

    @PostMapping("/generate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalRes<String>> generate(
            @RequestBody GeneratePenggajianReq req) {
        return ResponseEntity.ok(penggajianService.generate(req));
    }

    @PostMapping("/kirim-slip/{penggajianId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HRD')")
    public ResponseEntity<GlobalRes<String>> kirimSlipGaji(
            @PathVariable Long penggajianId) {
        return ResponseEntity.ok(penggajianService.kirimSlipGaji(penggajianId));
    }
}
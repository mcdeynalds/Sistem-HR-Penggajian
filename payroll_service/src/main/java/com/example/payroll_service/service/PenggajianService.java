package com.example.payroll_service.service;

import com.example.payroll_service.payload.req.GeneratePenggajianReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.payload.res.PenggajianRes;
import java.util.List;

public interface PenggajianService {
    GlobalRes<List<PenggajianRes>> ambilSemua();
    GlobalRes<PenggajianRes> ambilById(Long id);
    GlobalRes<List<PenggajianRes>> ambilByKaryawanId(Long karyawanId);
    GlobalRes<List<PenggajianRes>> ambilByBulanDanTahun(Integer bulan, Integer tahun);
    GlobalRes<String> generate(GeneratePenggajianReq req);
    GlobalRes<String> kirimSlipGaji(Long penggajianId);
}
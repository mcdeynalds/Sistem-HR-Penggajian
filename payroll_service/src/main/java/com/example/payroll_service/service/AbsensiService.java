package com.example.payroll_service.service;

import com.example.payroll_service.payload.req.RingkasanAbsensiReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.payload.res.RingkasanAbsensiRes;
import java.util.List;

public interface AbsensiService {
    GlobalRes<List<RingkasanAbsensiRes>> ambilSemua();
    GlobalRes<RingkasanAbsensiRes> ambilByKaryawanDanBulan(Long karyawanId, Integer bulan, Integer tahun);
    GlobalRes<RingkasanAbsensiRes> tambah(RingkasanAbsensiReq req);
    GlobalRes<RingkasanAbsensiRes> update(Long id, RingkasanAbsensiReq req);
}
package com.example.payroll_service.service;

import com.example.payroll_service.payload.req.KomponenGajiReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.payload.res.KomponenGajiRes;
import java.util.List;

public interface KomponenGajiService {
    GlobalRes<List<KomponenGajiRes>> ambilSemua();
    GlobalRes<KomponenGajiRes> ambilByKaryawanId(Long karyawanId);
    GlobalRes<KomponenGajiRes> tambah(KomponenGajiReq req);
    GlobalRes<KomponenGajiRes> update(Long id, KomponenGajiReq req);
    GlobalRes<String> hapus(Long id);
}
package com.example.payroll_service.service.impl;

import com.example.payroll_service.entity.RingkasanAbsensiEntity;
import com.example.payroll_service.payload.req.RingkasanAbsensiReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.payload.res.RingkasanAbsensiRes;
import com.example.payroll_service.repository.RingkasanAbsensiRepository;
import com.example.payroll_service.service.AbsensiService;
import com.example.payroll_service.utility.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsensiServiceImpl implements AbsensiService {

    @Autowired
    private RingkasanAbsensiRepository ringkasanAbsensiRepository;

    @Override
    public GlobalRes<List<RingkasanAbsensiRes>> ambilSemua() {
        try {
            List<RingkasanAbsensiRes> list = ringkasanAbsensiRepository.findAll()
                    .stream()
                    .map(this::keRes)
                    .collect(Collectors.toList());
            return new GlobalRes<>(true, Message.ABSENSI_FOUND, list);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<RingkasanAbsensiRes> ambilByKaryawanDanBulan(
            Long karyawanId, Integer bulan, Integer tahun) {
        try {
            RingkasanAbsensiEntity absensi = ringkasanAbsensiRepository
                    .findByKaryawanIdAndBulanAndTahun(karyawanId, bulan, tahun)
                    .orElseThrow(() -> new RuntimeException(Message.ABSENSI_NOT_FOUND));
            return new GlobalRes<>(true, Message.ABSENSI_FOUND, keRes(absensi));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<RingkasanAbsensiRes> tambah(RingkasanAbsensiReq req) {
        try {
            if (ringkasanAbsensiRepository.existsByKaryawanIdAndBulanAndTahun(
                    req.getKaryawanId(), req.getBulan(), req.getTahun())) {
                return new GlobalRes<>(false, Message.ABSENSI_EXISTS, null);
            }

            RingkasanAbsensiEntity absensi = new RingkasanAbsensiEntity();
            absensi.setKaryawanId(req.getKaryawanId());
            absensi.setNamaKaryawan(req.getNamaKaryawan());
            absensi.setBulan(req.getBulan());
            absensi.setTahun(req.getTahun());
            absensi.setTotalHadir(req.getTotalHadir());
            absensi.setTotalAbsen(req.getTotalAbsen());
            absensi.setTotalTerlambat(req.getTotalTerlambat());
            absensi.setJamLembur(req.getJamLembur());

            ringkasanAbsensiRepository.save(absensi);

            return new GlobalRes<>(true, Message.ABSENSI_CREATED, keRes(absensi));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<RingkasanAbsensiRes> update(Long id, RingkasanAbsensiReq req) {
        try {
            RingkasanAbsensiEntity absensi = ringkasanAbsensiRepository
                    .findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.ABSENSI_NOT_FOUND));

            absensi.setTotalHadir(req.getTotalHadir());
            absensi.setTotalAbsen(req.getTotalAbsen());
            absensi.setTotalTerlambat(req.getTotalTerlambat());
            absensi.setJamLembur(req.getJamLembur());

            ringkasanAbsensiRepository.save(absensi);

            return new GlobalRes<>(true, Message.ABSENSI_UPDATED, keRes(absensi));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    private RingkasanAbsensiRes keRes(RingkasanAbsensiEntity absensi) {
        RingkasanAbsensiRes res = new RingkasanAbsensiRes();
        res.setId(absensi.getId());
        res.setKaryawanId(absensi.getKaryawanId());
        res.setNamaKaryawan(absensi.getNamaKaryawan());
        res.setBulan(absensi.getBulan());
        res.setTahun(absensi.getTahun());
        res.setTotalHadir(absensi.getTotalHadir());
        res.setTotalAbsen(absensi.getTotalAbsen());
        res.setTotalTerlambat(absensi.getTotalTerlambat());
        res.setJamLembur(absensi.getJamLembur());
        return res;
    }
}
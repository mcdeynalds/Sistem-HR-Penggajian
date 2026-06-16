package com.example.payroll_service.service.impl;

import com.example.payroll_service.entity.KomponenGajiEntity;
import com.example.payroll_service.payload.req.KomponenGajiReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.payload.res.KomponenGajiRes;
import com.example.payroll_service.repository.KomponenGajiRepository;
import com.example.payroll_service.service.KomponenGajiService;
import com.example.payroll_service.utility.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KomponenGajiServiceImpl implements KomponenGajiService {

    @Autowired
    private KomponenGajiRepository komponenGajiRepository;

    @Override
    public GlobalRes<List<KomponenGajiRes>> ambilSemua() {
        try {
            List<KomponenGajiRes> list = komponenGajiRepository.findAll()
                    .stream()
                    .map(this::keRes)
                    .collect(Collectors.toList());
            return new GlobalRes<>(true, Message.KOMPONEN_GAJI_FOUND, list);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<KomponenGajiRes> ambilByKaryawanId(Long karyawanId) {
        try {
            KomponenGajiEntity komponen = komponenGajiRepository
                    .findByKaryawanId(karyawanId)
                    .orElseThrow(() -> new RuntimeException(Message.KOMPONEN_GAJI_NOT_FOUND));
            return new GlobalRes<>(true, Message.KOMPONEN_GAJI_FOUND, keRes(komponen));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<KomponenGajiRes> tambah(KomponenGajiReq req) {
        try {
            if (komponenGajiRepository.existsByKaryawanId(req.getKaryawanId())) {
                return new GlobalRes<>(false, Message.KOMPONEN_GAJI_EXISTS, null);
            }

            KomponenGajiEntity komponen = new KomponenGajiEntity();
            komponen.setKaryawanId(req.getKaryawanId());
            komponen.setGajiPokok(req.getGajiPokok());
            komponen.setTunjangan(req.getTunjangan());
            komponen.setPotongan(req.getPotongan());

            komponenGajiRepository.save(komponen);

            return new GlobalRes<>(true, Message.KOMPONEN_GAJI_CREATED, keRes(komponen));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<KomponenGajiRes> update(Long id, KomponenGajiReq req) {
        try {
            KomponenGajiEntity komponen = komponenGajiRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.KOMPONEN_GAJI_NOT_FOUND));

            komponen.setGajiPokok(req.getGajiPokok());
            komponen.setTunjangan(req.getTunjangan());
            komponen.setPotongan(req.getPotongan());

            komponenGajiRepository.save(komponen);

            return new GlobalRes<>(true, Message.KOMPONEN_GAJI_UPDATED, keRes(komponen));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<String> hapus(Long id) {
        try {
            KomponenGajiEntity komponen = komponenGajiRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.KOMPONEN_GAJI_NOT_FOUND));
            komponenGajiRepository.delete(komponen);
            return new GlobalRes<>(true, Message.KOMPONEN_GAJI_DELETED, null);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    private KomponenGajiRes keRes(KomponenGajiEntity komponen) {
        KomponenGajiRes res = new KomponenGajiRes();
        res.setId(komponen.getId());
        res.setKaryawanId(komponen.getKaryawanId());
        res.setGajiPokok(komponen.getGajiPokok());
        res.setTunjangan(komponen.getTunjangan());
        res.setPotongan(komponen.getPotongan());
        res.setTotalGaji(komponen.getGajiPokok()
                + komponen.getTunjangan()
                - komponen.getPotongan());
        res.setIsAktif(komponen.getIsAktif());
        return res;
    }
}
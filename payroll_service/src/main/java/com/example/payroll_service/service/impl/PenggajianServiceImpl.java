package com.example.payroll_service.service.impl;

import com.example.payroll_service.entity.KomponenGajiEntity;
import com.example.payroll_service.entity.PenggajianEntity;
import com.example.payroll_service.payload.req.EmailReq;
import com.example.payroll_service.payload.req.GeneratePenggajianReq;
import com.example.payroll_service.payload.res.*;
import com.example.payroll_service.repository.KomponenGajiRepository;
import com.example.payroll_service.repository.PenggajianRepository;
import com.example.payroll_service.service.LayananEmailService;
import com.example.payroll_service.service.PenggajianService;
import com.example.payroll_service.utility.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PenggajianServiceImpl implements PenggajianService {

    @Autowired
    private PenggajianRepository penggajianRepository;

    @Autowired
    private KomponenGajiRepository komponenGajiRepository;

    @Autowired
    private LayananEmailService layananEmailService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("employeeServiceUrl")
    private String employeeServiceUrl;

    @Override
    public GlobalRes<List<PenggajianRes>> ambilSemua() {
        try {
            List<PenggajianRes> list = penggajianRepository.findAll()
                    .stream().map(this::keRes).collect(Collectors.toList());
            return new GlobalRes<>(true, Message.PENGGAJIAN_FOUND, list);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<PenggajianRes> ambilById(Long id) {
        try {
            PenggajianEntity penggajian = penggajianRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.PENGGAJIAN_NOT_FOUND));
            return new GlobalRes<>(true, Message.PENGGAJIAN_FOUND, keRes(penggajian));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<List<PenggajianRes>> ambilByKaryawanId(Long karyawanId) {
        try {
            List<PenggajianRes> list = penggajianRepository
                    .findByKaryawanId(karyawanId)
                    .stream().map(this::keRes).collect(Collectors.toList());
            return new GlobalRes<>(true, Message.PENGGAJIAN_FOUND, list);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<List<PenggajianRes>> ambilByBulanDanTahun(
            Integer bulan, Integer tahun) {
        try {
            List<PenggajianRes> list = penggajianRepository
                    .findByBulanAndTahun(bulan, tahun)
                    .stream().map(this::keRes).collect(Collectors.toList());
            return new GlobalRes<>(true, Message.PENGGAJIAN_FOUND, list);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<String> generate(GeneratePenggajianReq req) {
        int berhasil = 0;
        int gagal = 0;

        try {
            // Ambil semua karyawan dari Employee Service
            String url = employeeServiceUrl + "/employee";
            ResponseEntity<GlobalRes<List<KaryawanRes>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<GlobalRes<List<KaryawanRes>>>() {}
            );

            List<KaryawanRes> daftarKaryawan = response.getBody().getData();

            if (daftarKaryawan == null || daftarKaryawan.isEmpty()) {
                return new GlobalRes<>(false, Message.KARYAWAN_NOT_FOUND, null);
            }

            for (KaryawanRes karyawan : daftarKaryawan) {
                try {
                    // Skip kalau sudah pernah generate bulan ini
                    if (penggajianRepository.existsByKaryawanIdAndBulanAndTahun(
                            karyawan.getId(), req.getBulan(), req.getTahun())) {
                        continue;
                    }

                    // Ambil komponen gaji karyawan
                    KomponenGajiEntity komponen = komponenGajiRepository
                            .findByKaryawanId(karyawan.getId())
                            .orElse(null);

                    // Kalau belum ada komponen gaji pakai default
                    double gajiPokok  = komponen != null ? komponen.getGajiPokok()  : 3000000.0;
                    double tunjangan  = komponen != null ? komponen.getTunjangan()  : 500000.0;
                    double potongan   = komponen != null ? komponen.getPotongan()   : 100000.0;
                    double totalGaji  = gajiPokok + tunjangan - potongan;

                    // Buat record penggajian
                    PenggajianEntity penggajian = new PenggajianEntity();
                    penggajian.setKaryawanId(karyawan.getId());
                    penggajian.setNamaKaryawan(karyawan.getFullName());
                    penggajian.setEmailKaryawan(karyawan.getEmail());
                    penggajian.setGajiPokok(gajiPokok);
                    penggajian.setTunjangan(tunjangan);
                    penggajian.setPotongan(potongan);
                    penggajian.setTotalGaji(totalGaji);
                    penggajian.setBulan(req.getBulan());
                    penggajian.setTahun(req.getTahun());
                    penggajian.setTanggalBayar(LocalDate.now());
                    penggajian.setSudahDikirim(false);

                    penggajianRepository.save(penggajian);
                    berhasil++;

                } catch (Exception e) {
                    gagal++;
                }
            }

            String hasil = "Generate penggajian selesai. Berhasil: "
                    + berhasil + ", Gagal: " + gagal;
            return new GlobalRes<>(true, Message.PENGGAJIAN_GENERATED, hasil);

        } catch (Exception e) {
            return new GlobalRes<>(false, Message.PENGGAJIAN_FAILED
                    + ": " + e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<String> kirimSlipGaji(Long penggajianId) {
        try {
            PenggajianEntity penggajian = penggajianRepository.findById(penggajianId)
                    .orElseThrow(() -> new RuntimeException(Message.PENGGAJIAN_NOT_FOUND));

            String isiEmail = "Halo " + penggajian.getNamaKaryawan() + ",\n\n"
                    + "Berikut slip gaji kamu bulan "
                    + penggajian.getBulan() + "/" + penggajian.getTahun() + ":\n\n"
                    + "Gaji Pokok  : Rp " + String.format("%,.0f", penggajian.getGajiPokok()) + "\n"
                    + "Tunjangan   : Rp " + String.format("%,.0f", penggajian.getTunjangan()) + "\n"
                    + "Potongan    : Rp " + String.format("%,.0f", penggajian.getPotongan()) + "\n"
                    + "────────────────────────\n"
                    + "Total Gaji  : Rp " + String.format("%,.0f", penggajian.getTotalGaji()) + "\n\n"
                    + "Salam,\nTim HR";

            EmailReq emailReq = new EmailReq();
            emailReq.setTo(penggajian.getEmailKaryawan());
            emailReq.setSubject("Slip Gaji Bulan " + penggajian.getBulan()
                    + "/" + penggajian.getTahun()
                    + " - " + penggajian.getNamaKaryawan());
            emailReq.setBody(isiEmail);

            layananEmailService.kirimEmail(emailReq);

            penggajian.setSudahDikirim(true);
            penggajianRepository.save(penggajian);

            return new GlobalRes<>(true, Message.PENGGAJIAN_SENT, null);

        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    private PenggajianRes keRes(PenggajianEntity penggajian) {
        PenggajianRes res = new PenggajianRes();
        res.setId(penggajian.getId());
        res.setKaryawanId(penggajian.getKaryawanId());
        res.setNamaKaryawan(penggajian.getNamaKaryawan());
        res.setEmailKaryawan(penggajian.getEmailKaryawan());
        res.setGajiPokok(penggajian.getGajiPokok());
        res.setTunjangan(penggajian.getTunjangan());
        res.setPotongan(penggajian.getPotongan());
        res.setTotalGaji(penggajian.getTotalGaji());
        res.setTanggalBayar(penggajian.getTanggalBayar() != null ?
                penggajian.getTanggalBayar().toString() : null);
        res.setBulan(penggajian.getBulan());
        res.setTahun(penggajian.getTahun());
        res.setSudahDikirim(penggajian.getSudahDikirim());
        return res;
    }
}
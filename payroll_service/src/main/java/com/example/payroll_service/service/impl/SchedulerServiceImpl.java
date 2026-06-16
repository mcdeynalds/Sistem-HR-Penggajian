package com.example.payroll_service.service.impl;

import com.example.payroll_service.payload.req.GeneratePenggajianReq;
import com.example.payroll_service.service.PenggajianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@EnableScheduling
public class SchedulerServiceImpl {

    @Autowired
    private PenggajianService penggajianService;

    // Fixed Rate: log status setiap 60 detik
    @Scheduled(fixedRate = 60000)
    public void checkStatusPayroll() {
        System.out.println("[SCHEDULER - Fixed Rate] Payroll Service berjalan normal - "
                + LocalDate.now());
    }

    // Fixed Delay: cek koneksi ke Employee Service setiap 30 detik
    @Scheduled(fixedDelay = 30000)
    public void checkKoneksiEmployeeService() {
        System.out.println("[SCHEDULER - Fixed Delay] Mengecek koneksi ke Employee Service - "
                + LocalDate.now());
    }

    // Cron: generate penggajian otomatis setiap tanggal 25 jam 08.00
    @Scheduled(cron = "0 0 8 25 * ?")
    public void autoGeneratePenggajian() {
        LocalDate sekarang = LocalDate.now();
        GeneratePenggajianReq req = new GeneratePenggajianReq();
        req.setBulan(sekarang.getMonthValue());
        req.setTahun(sekarang.getYear());

        System.out.println("[SCHEDULER - Cron] Auto generate penggajian bulan "
                + sekarang.getMonthValue() + "/" + sekarang.getYear());

        penggajianService.generate(req);
    }
}
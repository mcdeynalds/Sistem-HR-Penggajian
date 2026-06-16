package com.example.employee_service.service.impl;

import com.example.employee_service.entity.EmployeeEntity;
import com.example.employee_service.payload.req.EmailReq;
import com.example.employee_service.repository.EmployeeRepository;
import com.example.employee_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class SchedulerServiceImpl {

    private final EmployeeRepository employeeRepository;

    private final EmailService emailService;

    public SchedulerServiceImpl(EmployeeRepository employeeRepository,
            EmailService emailService) {
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }

    // Fixed Rate: log jumlah karyawan aktif setiap 30 detik
    @Scheduled(fixedRate = 30000)
    public void checkActiveEmployees() {
        List<EmployeeEntity> activeEmployees = employeeRepository.findByIsActive(true);
        System.out.println("[SCHEDULER - Fixed Rate] Jumlah karyawan aktif: "
                + activeEmployees.size());
    }

    // Fixed Delay: cek karyawan tanpa departemen, 5 detik setelah task sebelumnya
    // selesai
    @Scheduled(fixedDelay = 5000)
    public void checkEmployeeWithoutDepartment() {
        long count = employeeRepository.findAll()
                .stream()
                .filter(e -> e.getDepartment() == null)
                .count();
        if (count > 0) {
            System.out.println("[SCHEDULER - Fixed Delay] Ada " + count
                    + " karyawan belum assign ke departemen!");
        }
    }

    // Cron: kirim reminder ke semua karyawan setiap hari Senin jam 08.00
    @Scheduled(cron = "0 0 8 * * MON")
    public void sendWeeklyReminder() {
        List<EmployeeEntity> employees = employeeRepository.findByIsActive(true);
        for (EmployeeEntity emp : employees) {
            try {
                EmailReq emailReq = new EmailReq();
                emailReq.setTo(emp.getEmail());
                emailReq.setSubject("Reminder Mingguan - " + emp.getFullName());
                emailReq.setBody("Halo " + emp.getFullName()
                        + ",\n\nSelamat bekerja minggu ini! "
                        + "\n\nSalam,\nHR Team");
                emailService.sendEmail(emailReq);
            } catch (Exception e) {
                System.out.println("[SCHEDULER - Cron] Gagal kirim email ke "
                        + emp.getEmail() + ": " + e.getMessage());
            }
        }
        System.out.println("[SCHEDULER - Cron] Weekly reminder terkirim ke "
                + employees.size() + " karyawan");
    }
}
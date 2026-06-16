package com.example.payroll_service.payload.req;

import lombok.Data;

@Data
public class RingkasanAbsensiReq {
    private Long karyawanId;
    private String namaKaryawan;
    private Integer bulan;
    private Integer tahun;
    private Integer totalHadir;
    private Integer totalAbsen;
    private Integer totalTerlambat;
    private Double jamLembur;
}
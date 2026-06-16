package com.example.payroll_service.payload.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RingkasanAbsensiRes {
    private Long id;
    private Long karyawanId;
    private String namaKaryawan;
    private Integer bulan;
    private Integer tahun;
    private Integer totalHadir;
    private Integer totalAbsen;
    private Integer totalTerlambat;
    private Double jamLembur;
}
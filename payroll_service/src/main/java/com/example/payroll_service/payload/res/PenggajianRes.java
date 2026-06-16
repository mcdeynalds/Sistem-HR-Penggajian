package com.example.payroll_service.payload.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenggajianRes {
    private Long id;
    private Long karyawanId;
    private String namaKaryawan;
    private String emailKaryawan;
    private Double gajiPokok;
    private Double tunjangan;
    private Double potongan;
    private Double totalGaji;
    private String tanggalBayar;
    private Integer bulan;
    private Integer tahun;
    private Boolean sudahDikirim;
}
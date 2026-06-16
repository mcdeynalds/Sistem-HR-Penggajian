package com.example.payroll_service.payload.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KomponenGajiRes {
    private Long id;
    private Long karyawanId;
    private Double gajiPokok;
    private Double tunjangan;
    private Double potongan;
    private Double totalGaji;
    private Boolean isAktif;
}
package com.example.payroll_service.payload.req;

import lombok.Data;

@Data
public class KomponenGajiReq {
    private Long karyawanId;
    private Double gajiPokok;
    private Double tunjangan;
    private Double potongan;
}
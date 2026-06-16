package com.example.payroll_service.payload.req;

import lombok.Data;

@Data
public class GeneratePenggajianReq {
    private Integer bulan;
    private Integer tahun;
}
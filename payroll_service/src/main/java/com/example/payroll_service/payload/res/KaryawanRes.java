package com.example.payroll_service.payload.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KaryawanRes {
    private Long id;
    private String nik;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String departmentName;
    private Boolean isActive;
}
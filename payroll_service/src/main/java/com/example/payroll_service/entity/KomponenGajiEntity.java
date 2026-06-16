package com.example.payroll_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "komponen_gaji")
public class KomponenGajiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "karyawan_id", nullable = false, unique = true)
    private Long karyawanId;

    @Column(name = "gaji_pokok", nullable = false)
    private Double gajiPokok;

    @Column(name = "tunjangan")
    private Double tunjangan;

    @Column(name = "potongan")
    private Double potongan;

    @Column(name = "is_aktif")
    private Boolean isAktif = true;
}
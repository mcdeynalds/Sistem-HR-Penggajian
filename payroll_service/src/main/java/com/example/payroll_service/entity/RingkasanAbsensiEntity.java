package com.example.payroll_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ringkasan_absensi")
public class RingkasanAbsensiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "karyawan_id", nullable = false)
    private Long karyawanId;

    @Column(name = "nama_karyawan")
    private String namaKaryawan;

    @Column(name = "bulan", nullable = false)
    private Integer bulan;

    @Column(name = "tahun", nullable = false)
    private Integer tahun;

    @Column(name = "total_hadir")
    private Integer totalHadir;

    @Column(name = "total_absen")
    private Integer totalAbsen;

    @Column(name = "total_terlambat")
    private Integer totalTerlambat;

    @Column(name = "jam_lembur")
    private Double jamLembur;
}
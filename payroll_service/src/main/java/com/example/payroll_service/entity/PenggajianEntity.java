package com.example.payroll_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "penggajian")
public class PenggajianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "karyawan_id", nullable = false)
    private Long karyawanId;

    @Column(name = "nama_karyawan")
    private String namaKaryawan;

    @Column(name = "email_karyawan")
    private String emailKaryawan;

    @Column(name = "gaji_pokok")
    private Double gajiPokok;

    @Column(name = "tunjangan")
    private Double tunjangan;

    @Column(name = "potongan")
    private Double potongan;

    @Column(name = "total_gaji")
    private Double totalGaji;

    @Column(name = "tanggal_bayar")
    private LocalDate tanggalBayar;

    @Column(name = "bulan")
    private Integer bulan;

    @Column(name = "tahun")
    private Integer tahun;

    @Column(name = "sudah_dikirim")
    private Boolean sudahDikirim = false;
}
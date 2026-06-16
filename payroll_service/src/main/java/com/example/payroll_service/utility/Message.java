package com.example.payroll_service.utility;

public class Message {
    // Komponen Gaji
    public static final String KOMPONEN_GAJI_FOUND = "Data komponen gaji ditemukan";
    public static final String KOMPONEN_GAJI_NOT_FOUND = "Data komponen gaji tidak ditemukan";
    public static final String KOMPONEN_GAJI_CREATED = "Komponen gaji berhasil ditambahkan";
    public static final String KOMPONEN_GAJI_UPDATED = "Komponen gaji berhasil diupdate";
    public static final String KOMPONEN_GAJI_DELETED = "Komponen gaji berhasil dihapus";
    public static final String KOMPONEN_GAJI_EXISTS = "Komponen gaji untuk karyawan ini sudah ada";

    // Penggajian
    public static final String PENGGAJIAN_FOUND = "Data penggajian ditemukan";
    public static final String PENGGAJIAN_NOT_FOUND = "Data penggajian tidak ditemukan";
    public static final String PENGGAJIAN_GENERATED = "Penggajian berhasil digenerate";
    public static final String PENGGAJIAN_EXISTS = "Penggajian bulan ini sudah pernah digenerate";
    public static final String PENGGAJIAN_SENT = "Slip gaji berhasil dikirim";
    public static final String PENGGAJIAN_FAILED = "Gagal generate penggajian";

    // Absensi
    public static final String ABSENSI_FOUND = "Data absensi ditemukan";
    public static final String ABSENSI_NOT_FOUND = "Data absensi tidak ditemukan";
    public static final String ABSENSI_CREATED = "Data absensi berhasil ditambahkan";
    public static final String ABSENSI_UPDATED = "Data absensi berhasil diupdate";
    public static final String ABSENSI_EXISTS = "Data absensi bulan ini sudah ada";

    // Email
    public static final String EMAIL_SUCCESS = "Email berhasil dikirim";
    public static final String EMAIL_FAILED = "Email gagal dikirim";

    // Karyawan
    public static final String KARYAWAN_NOT_FOUND = "Data karyawan tidak ditemukan di Employee Service";
    public static final String KARYAWAN_SERVICE_ERROR = "Gagal menghubungi Employee Service";
}
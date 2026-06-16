package com.example.employee_service.utility;

public class Message {

    // Auth
    public static final String LOGIN_SUCCESS = "Login berhasil";
    public static final String LOGIN_FAILED = "Username atau password salah";
    public static final String LOGOUT_SUCCESS = "Logout berhasil";
    public static final String REGISTER_SUCCESS = "Registrasi berhasil";
    public static final String UNAUTHORIZED = "Anda tidak memiliki akses";

    // Employee
    public static final String EMPLOYEE_FOUND = "Data karyawan ditemukan";
    public static final String EMPLOYEE_NOT_FOUND = "Data karyawan tidak ditemukan";
    public static final String EMPLOYEE_CREATED = "Karyawan berhasil ditambahkan";
    public static final String EMPLOYEE_UPDATED = "Karyawan berhasil diupdate";
    public static final String EMPLOYEE_DELETED = "Karyawan berhasil dihapus";
    public static final String EMPLOYEE_EXISTS = "NIK atau email karyawan sudah terdaftar";

    // Department
    public static final String DEPARTMENT_FOUND = "Data departemen ditemukan";
    public static final String DEPARTMENT_NOT_FOUND = "Departemen tidak ditemukan";
    public static final String DEPARTMENT_CREATED = "Departemen berhasil ditambahkan";
    public static final String DEPARTMENT_UPDATED = "Departemen berhasil diupdate";
    public static final String DEPARTMENT_DELETED = "Departemen berhasil dihapus";
    public static final String DEPARTMENT_EXISTS = "Nama departemen sudah terdaftar";

    // Email
    public static final String EMAIL_SUCCESS = "Email berhasil dikirim";
    public static final String EMAIL_FAILED = "Email gagal dikirim";

    // Excel
    public static final String EXCEL_SUCCESS = "Import Excel berhasil";
    public static final String EXCEL_FAILED = "Import Excel gagal";
    public static final String EXCEL_EMPTY = "File Excel kosong atau format tidak sesuai";
}
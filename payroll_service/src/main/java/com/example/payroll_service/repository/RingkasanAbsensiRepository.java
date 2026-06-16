package com.example.payroll_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.payroll_service.entity.RingkasanAbsensiEntity;

@Repository
public interface RingkasanAbsensiRepository extends JpaRepository<RingkasanAbsensiEntity, Long> {
    Optional<RingkasanAbsensiEntity> findByKaryawanIdAndBulanAndTahun(Long karyawanId, Integer bulan, Integer tahun);
    List<RingkasanAbsensiEntity> findByBulanAndTahun(Integer bulan, Integer tahun);
    Boolean existsByKaryawanIdAndBulanAndTahun(Long karyawanId, Integer bulan, Integer tahun);
}
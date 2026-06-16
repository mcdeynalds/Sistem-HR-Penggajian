package com.example.payroll_service.repository;

import com.example.payroll_service.entity.PenggajianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PenggajianRepository extends JpaRepository<PenggajianEntity, Long> {
    List<PenggajianEntity> findByKaryawanId(Long karyawanId);
    List<PenggajianEntity> findByBulanAndTahun(Integer bulan, Integer tahun);
    Optional<PenggajianEntity> findByKaryawanIdAndBulanAndTahun(Long karyawanId, Integer bulan, Integer tahun);
    Boolean existsByKaryawanIdAndBulanAndTahun(Long karyawanId, Integer bulan, Integer tahun);
}
package com.example.payroll_service.repository;

import com.example.payroll_service.entity.KomponenGajiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KomponenGajiRepository extends JpaRepository<KomponenGajiEntity, Long> {
    Optional<KomponenGajiEntity> findByKaryawanId(Long karyawanId);
    Boolean existsByKaryawanId(Long karyawanId);
}
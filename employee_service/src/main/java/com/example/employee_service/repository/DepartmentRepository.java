package com.example.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.example.employee_service.entity.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    Optional<DepartmentEntity> findByDepartmentName(String departmentName);

    Boolean existsByDepartmentName(String departmentName);
}

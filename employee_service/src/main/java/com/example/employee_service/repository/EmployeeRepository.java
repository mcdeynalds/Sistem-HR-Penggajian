package com.example.employee_service.repository;

import com.example.employee_service.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByNik(String nik);

    Optional<EmployeeEntity> findByEmail(String email);

    Boolean existsByNik(String nik);

    Boolean existsByEmail(String email);

    List<EmployeeEntity> findByIsActive(Boolean isActive);

    List<EmployeeEntity> findByDepartmentId(Long departmentId);
}
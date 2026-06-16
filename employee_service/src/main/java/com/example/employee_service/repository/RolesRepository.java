package com.example.employee_service.repository;

import com.example.employee_service.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
    Optional<RolesEntity> findByRoleName(String roleName);
}
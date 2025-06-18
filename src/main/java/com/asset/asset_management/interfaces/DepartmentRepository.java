package com.asset.asset_management.interfaces;

import com.asset.asset_management.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {}

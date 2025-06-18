package com.asset.asset_management.interfaces;

import com.asset.asset_management.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}

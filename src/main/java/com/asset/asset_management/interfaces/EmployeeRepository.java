package com.asset.asset_management.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asset.asset_management.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long id);
}

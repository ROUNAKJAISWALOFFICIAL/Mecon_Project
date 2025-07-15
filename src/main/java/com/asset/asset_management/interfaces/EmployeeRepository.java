package com.asset.asset_management.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.asset_management.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment_Id(Long departmentId); 

    List<Employee> findByJoinDateBetween(LocalDate startDate, LocalDate endDate);
    List<Employee> findByDepartment_IdAndJoinDateBetween(Long departmentId, LocalDate startDate, LocalDate endDate);
}
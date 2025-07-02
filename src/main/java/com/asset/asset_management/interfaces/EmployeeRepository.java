package com.asset.asset_management.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.asset_management.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // THIS IS THE CRITICAL LINE:
    // The method to find employees by their associated Department's ID.
    // It's `findBy` + `Department` (the field name in Employee entity) + `_` + `Id` (the ID field of Department entity)
    List<Employee> findByDepartment_Id(Long departmentId); // Correct method name

    List<Employee> findByJoinDateBetween(LocalDate startDate, LocalDate endDate);
    List<Employee> findByDepartment_IdAndJoinDateBetween(Long departmentId, LocalDate startDate, LocalDate endDate);
}
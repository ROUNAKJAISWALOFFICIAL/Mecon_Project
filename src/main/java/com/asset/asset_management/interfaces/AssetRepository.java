// src/main/java/com/asset/asset_management/interfaces/AssetRepository.java
package com.asset.asset_management.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.asset_management.entities.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    // Get assets by category ID
    List<Asset> findByCategory_Id(Long categoryId);

    // Get unassigned assets with a specific status
    List<Asset> findByAssignToIsNullAndStatusIgnoreCase(String status);

    // Count all assigned assets
    long countByAssignToIsNotNull();

    // Count assets by status
    long countByStatus(String status);

    // (Optional) Get all assigned assets
    List<Asset> findByAssignToIsNotNull();

    // (Optional) Get assets by status (like "available", "under maintenance")
    List<Asset> findByStatusIgnoreCase(String status);

    // --- NEW METHODS FOR REPORTING FILTERS ---

    // Get assets by the ID of the department the assigned employee belongs to
    List<Asset> findByAssignTo_Department_Id(Long departmentId);

    // Get assets by category and status
    List<Asset> findByCategory_IdAndStatusIgnoreCase(Long categoryId, String status);

    // Get assets by assigned employee's department and status
    List<Asset> findByAssignTo_Department_IdAndStatusIgnoreCase(Long departmentId, String status);

    // Get assets by assigned employee's ID and status (e.g., assets assigned to an employee that are also "UNDER_MAINTENANCE")
    List<Asset> findByAssignTo_IdAndStatusIgnoreCase(Long employeeId, String status);

    // Get assets by assigned employee's ID
    List<Asset> findByAssignTo_Id(Long employeeId);

    // Combinations of filters (these can get long, consider Specification API for more complexity)
    List<Asset> findByAssignTo_Department_IdAndCategory_IdAndStatusIgnoreCase(Long departmentId, Long categoryId, String status);
    List<Asset> findByAssignTo_Department_IdAndCategory_Id(Long departmentId, Long categoryId);

}
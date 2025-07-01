package com.asset.asset_management.interfaces; // Updated package name

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Added for clarity, though not strictly required by JpaRepository

import com.asset.asset_management.entities.Asset; // Corrected import

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByCategory_Id(Long categoryId);
    List<Asset> findByAssignToIsNullAndStatusIgnoreCase(String status);

    // Custom method to count assets where assignTo is not null (i.e., assigned)
    long countByAssignToIsNotNull();

    // Method to count assets by status (e.g., "Maintenance")
    long countByStatus(String status);
}
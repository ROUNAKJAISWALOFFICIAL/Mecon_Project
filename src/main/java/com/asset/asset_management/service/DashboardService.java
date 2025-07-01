package com.asset.asset_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Corrected import

import com.asset.asset_management.interfaces.AssetRepository; // Corrected import
import com.asset.asset_management.interfaces.EmployeeRepository;
import com.asset.asset_management.model.DashboardSummary;

@Service
public class DashboardService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public DashboardSummary getDashboardSummary() {
        long totalAssets = assetRepository.count();
        
        // Count assets explicitly assigned to an employee (where assignTo is not null)
        long assignedAssets = assetRepository.countByAssignToIsNotNull();
        
        // Count assets explicitly marked with "Available" status

 long availableAssets = assetRepository.countByStatus("Available");
        // Count assets explicitly marked with "Under Maintenance" status
        long assetsInMaintenance = assetRepository.countByStatus("Under Maintenance"); 
        
        long totalEmployees = employeeRepository.count();

        return new DashboardSummary(totalAssets, availableAssets, assignedAssets, assetsInMaintenance, totalEmployees);
    }
}
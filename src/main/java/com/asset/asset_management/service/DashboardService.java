package com.asset.asset_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset.asset_management.interfaces.AssetRepository;
import com.asset.asset_management.interfaces.EmployeeRepository;
import com.asset.asset_management.interfaces.AssetRequestRepository;
import com.asset.asset_management.model.DashboardSummary;

@Service
public class DashboardService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AssetRequestRepository assetRequestRepository;

    public DashboardSummary getDashboardSummary() {
        long totalAssets = assetRepository.count();
        long assignedAssets = assetRepository.countByAssignToIsNotNull();
        long availableAssets = assetRepository.countByStatus("Available");
        long assetsInMaintenance = assetRepository.countByStatus("Under Maintenance");
        long totalEmployees = employeeRepository.count();
        long pendingRequests = assetRequestRepository.countByStatus("Pending");

        return new DashboardSummary(
            totalAssets,
            availableAssets,
            assignedAssets,
            assetsInMaintenance,
            totalEmployees,
            pendingRequests
        );
    }
}

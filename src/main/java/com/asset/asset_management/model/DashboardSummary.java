package com.asset.asset_management.model;

public class DashboardSummary {

    private long totalAssets;
    private long availableAssets; 
    private long assignedAssets;
    private long assetsInMaintenance;
    private long totalEmployees;

    // Manual constructor
    public DashboardSummary(long totalAssets, long availableAssets, long assignedAssets, long assetsInMaintenance, long totalEmployees) {
        this.totalAssets = totalAssets;
        this.availableAssets = availableAssets;
        this.assignedAssets = assignedAssets;
        this.assetsInMaintenance = assetsInMaintenance;
        
        this.totalEmployees = totalEmployees;
    }

    // Getters and Setters
    public long getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(long totalAssets) {
        this.totalAssets = totalAssets;
    }
    public long getavailableAssets() {
        return availableAssets;
    }

    public void setavailableAssets(long availableAssets) {
        this.availableAssets = availableAssets;
    }

    public long getAssignedAssets() {
        return assignedAssets;
    }

    public void setAssignedAssets(long assignedAssets) {
        this.assignedAssets = assignedAssets;
    }

    public long getAssetsInMaintenance() {
        return assetsInMaintenance;
    }

    public void setAssetsInMaintenance(long assetsInMaintenance) {
        this.assetsInMaintenance = assetsInMaintenance;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }
}

package com.asset.asset_management.model;

public class DashboardSummary {
    private long totalAssets;
    private long availableAssets;
    private long assignedAssets;
    private long assetsInMaintenance;
    private long totalEmployees;
    private long pendingRequests;

    public DashboardSummary(long totalAssets, long availableAssets, long assignedAssets,
                            long assetsInMaintenance, long totalEmployees,
                            long pendingRequests) {
        this.totalAssets = totalAssets;
        this.availableAssets = availableAssets;
        this.assignedAssets = assignedAssets;
        this.assetsInMaintenance = assetsInMaintenance;
        this.totalEmployees = totalEmployees;
        this.pendingRequests = pendingRequests;
    }

    public long getTotalAssets() {
        return totalAssets;
    }

    public long getAvailableAssets() {
        return availableAssets;
    }

    public long getAssignedAssets() {
        return assignedAssets;
    }

    public long getAssetsInMaintenance() {
        return assetsInMaintenance;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public long getPendingRequests() {
        return pendingRequests;
    }
}

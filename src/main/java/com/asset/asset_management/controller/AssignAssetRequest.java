package com.asset.asset_management.controller;

public class AssignAssetRequest {
    private Long requestId;
    private Long employeeId;
    private Long assetId;

    // Getter and Setter for requestId
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    // Getter and Setter for employeeId
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    // Getter and Setter for assetId
    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }
}

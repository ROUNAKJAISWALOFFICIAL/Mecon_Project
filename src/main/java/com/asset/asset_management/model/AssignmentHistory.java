// src/main/java/com/asset/asset_management/dto/AssignmentHistoryDTO.java
package com.asset.asset_management.model;

import com.asset.asset_management.entities.Asset;

public class AssignmentHistory {
    private Long assetId;
    private String assetName;
    private String assetDescription;
    private String currentStatus;
    private String assignedToEmployeeName;
    private String employeeDesignation;
    private String employeeEmail;
    private String departmentName;
    // private LocalDate assignmentDate; // Add this if you track assignment date in Asset

    public AssignmentHistory(Asset asset) {
        this.assetId = asset.getId();
        this.assetName = asset.getName();
        this.assetDescription = asset.getDescription();
        this.currentStatus = asset.getStatus();
        if (asset.getAssignTo() != null) {
            this.assignedToEmployeeName = asset.getAssignTo().getName();
            this.employeeDesignation = asset.getAssignTo().getDesignation();
            this.employeeEmail = asset.getAssignTo().getEmail();
            this.departmentName = asset.getAssignTo().getDepartment() != null ?
                                  asset.getAssignTo().getDepartment().getDepartmentName() : "N/A";
        } else {
            this.assignedToEmployeeName = "Unassigned";
            this.employeeDesignation = "N/A";
            this.employeeEmail = "N/A";
            this.departmentName = "N/A";
        }
        // this.assignmentDate = asset.getAssignmentDate(); // Uncomment if asset has assignmentDate
    }

    // Getters
    public Long getAssetId() { return assetId; }
    public String getAssetName() { return assetName; }
    public String getAssetDescription() { return assetDescription; }
    public String getCurrentStatus() { return currentStatus; }
    public String getAssignedToEmployeeName() { return assignedToEmployeeName; }
    public String getEmployeeDesignation() { return employeeDesignation; }
    public String getEmployeeEmail() { return employeeEmail; }
    public String getDepartmentName() { return departmentName; }

    // Setters (if needed)
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public void setAssetName(String assetName) { this.assetName = assetName; }
    public void setAssetDescription(String assetDescription) { this.assetDescription = assetDescription; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
    public void setAssignedToEmployeeName(String assignedToEmployeeName) { this.assignedToEmployeeName = assignedToEmployeeName; }
    public void setEmployeeDesignation(String employeeDesignation) { this.employeeDesignation = employeeDesignation; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
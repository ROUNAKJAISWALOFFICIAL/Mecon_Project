package com.asset.asset_management.model;

import com.asset.asset_management.entities.Asset;
public class AssetReport {
    private Long id;
    private String name;
    private String description;
    private double value;
    private String status;
    private String categoryName;
    private String assignedToEmployeeName;
    private String departmentName; 

    // Constructor to map from Asset entity
    public AssetReport(Asset asset) {
        this.id = asset.getId();
        this.name = asset.getName();
        this.description = asset.getDescription();
        this.value = asset.getValue();
        this.status = asset.getStatus();
        this.categoryName = asset.getCategory() != null ? asset.getCategory().getName() : "N/A";
        this.assignedToEmployeeName = asset.getAssignTo() != null ? asset.getAssignTo().getName() : "Unassigned";
        this.departmentName = (asset.getAssignTo() != null && asset.getAssignTo().getDepartment() != null)
                              ? asset.getAssignTo().getDepartment().getDepartmentName()
                              : "N/A";
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getValue() { return value; }
    public String getStatus() { return status; }
    public String getCategoryName() { return categoryName; }
    public String getAssignedToEmployeeName() { return assignedToEmployeeName; }
    public String getDepartmentName() { return departmentName; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setValue(double value) { this.value = value; }
    public void setStatus(String status) { this.status = status; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setAssignedToEmployeeName(String assignedToEmployeeName) { this.assignedToEmployeeName = assignedToEmployeeName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
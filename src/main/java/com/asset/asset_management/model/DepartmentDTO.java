package com.asset.asset_management.model;

import com.asset.asset_management.entities.Department;

public class DepartmentDTO {
    private Long id;
    private String departmentName; 

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.departmentName = department.getDepartmentName();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
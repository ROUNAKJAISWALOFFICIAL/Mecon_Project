package com.asset.asset_management.model;

import com.asset.asset_management.entities.Employee;
import java.time.LocalDate;

public class EmployeeReportDTO {
    private Long id;
    private String name;
    private String designation;
    private String email;
    private String phone;
    private LocalDate joinDate; // New field for report
    private String departmentName; // Flattened field for report

    public EmployeeReportDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.designation = employee.getDesignation();
        this.email = employee.getEmail();
        this.phone = employee.getPhone();
        this.joinDate = employee.getJoinDate();
        // Handle null department gracefully
        this.departmentName = (employee.getDepartment() != null) ? employee.getDepartment().getDepartmentName() : "N/A";
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDesignation() { return designation; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getJoinDate() { return joinDate; }
    public String getDepartmentName() { return departmentName; }

    // Setters (optional, typically not needed for report DTOs)
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDesignation(String designation) { this.designation = designation; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
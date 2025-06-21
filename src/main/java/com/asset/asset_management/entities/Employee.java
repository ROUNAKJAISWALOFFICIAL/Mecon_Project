package com.asset.asset_management.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String designation;
    private String email;

    // ✅ Add these fields
    private String phone;

    private LocalDate joinDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    // Constructors
    public Employee() {}

    public Employee(String name, String designation, String email, String phone, LocalDate joinDate, Department department) {
        this.name = name;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
        this.joinDate = joinDate;
        this.department = department;
    }

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDesignation() { return designation; }

    public void setDesignation(String designation) { this.designation = designation; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getJoinDate() { return joinDate; }

    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public Department getDepartment() { return department; }

    public void setDepartment(Department department) { this.department = department; }
}

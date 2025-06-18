package com.asset.asset_management.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double value;
    private String status;

    // Constructors
    public Asset() {}

    public Asset(String name, String description, double value, String status) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}

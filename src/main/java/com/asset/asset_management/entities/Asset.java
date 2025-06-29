package com.asset.asset_management.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

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

@ManyToOne
@JoinColumn(name = "category_id")
@JsonBackReference
private AssetCategory category;


    @ManyToOne
    @JoinColumn(name = "assign_to")
    private Employee assignTo;

    public Asset() {}

    public Asset(String name, String description, double value, String status, AssetCategory category) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.status = status;
        this.category = category;
    }

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

    public AssetCategory getCategory() { return category; }
    public void setCategory(AssetCategory category) { this.category = category; }

    public Employee getAssignTo() { return assignTo; }
    public void setAssignTo(Employee assignTo) { this.assignTo = assignTo; }
}

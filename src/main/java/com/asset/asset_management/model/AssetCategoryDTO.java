// src/main/java/com/asset/asset_management/dto/AssetCategoryDTO.java
package com.asset.asset_management.model;

import com.asset.asset_management.entities.AssetCategory;

public class AssetCategoryDTO {
    private Long id;
    private String name;

    public AssetCategoryDTO(AssetCategory category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
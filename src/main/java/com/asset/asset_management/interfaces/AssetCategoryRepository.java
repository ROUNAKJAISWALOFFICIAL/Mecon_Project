package com.asset.asset_management.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asset.asset_management.entities.AssetCategory;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {}

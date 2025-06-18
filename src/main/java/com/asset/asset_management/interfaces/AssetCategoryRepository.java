package com.asset.asset_management.interfaces;

import com.asset.asset_management.entities.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {}

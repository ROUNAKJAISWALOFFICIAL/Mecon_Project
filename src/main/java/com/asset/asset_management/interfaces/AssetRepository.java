package com.asset.asset_management.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asset.asset_management.entities.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {
List<Asset> findByCategory_Id(Long categoryId);
}

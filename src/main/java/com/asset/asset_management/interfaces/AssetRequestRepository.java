package com.asset.asset_management.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asset.asset_management.entities.AssetRequest;

public interface AssetRequestRepository extends JpaRepository<AssetRequest, Long> {
}

package com.asset.asset_management.interfaces;

import com.asset.asset_management.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findTop5ByOrderByIdDesc();
}

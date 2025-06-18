package com.asset.asset_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.entities.Asset;
import com.asset.asset_management.interfaces.AssetRepository;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*")
public class AssetController {

    @Autowired
    private AssetRepository assetRepo;

    @GetMapping("/category/{categoryId}")
    public List<Asset> getAssetsByCategory(@PathVariable Long categoryId) {
        return assetRepo.findByCategory_Id(categoryId);
    }

    @GetMapping
    public List<Asset> getAllAssets() {
        return assetRepo.findAll();
    }
     @PostMapping
    public Asset createAsset(@RequestBody Asset asset) {
return assetRepo.save(asset);
    }
}

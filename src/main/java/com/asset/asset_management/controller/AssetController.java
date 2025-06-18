package com.asset.asset_management.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // ✅ UPDATE an asset (used for edit)
    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset updatedAsset) {
        Optional<Asset> optionalAsset = assetRepo.findById(id);

        if (optionalAsset.isPresent()) {
            Asset existingAsset = optionalAsset.get();
            existingAsset.setName(updatedAsset.getName());
            existingAsset.setDescription(updatedAsset.getDescription());
            existingAsset.setValue(updatedAsset.getValue());
            existingAsset.setStatus(updatedAsset.getStatus());
            existingAsset.setCategory(updatedAsset.getCategory());

            Asset savedAsset = assetRepo.save(existingAsset);
            return ResponseEntity.ok(savedAsset);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ DELETE an asset
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        if (assetRepo.existsById(id)) {
            assetRepo.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}

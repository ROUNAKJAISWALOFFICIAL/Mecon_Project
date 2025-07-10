package com.asset.asset_management.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.entities.Asset;
import com.asset.asset_management.entities.Employee;
import com.asset.asset_management.interfaces.AssetRepository;
import com.asset.asset_management.interfaces.EmployeeRepository;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*")
public class AssetController {

    @Autowired
    private AssetRepository assetRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

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

    @GetMapping("/available")
    public List<Asset> getAvailableAssets() {
        return assetRepo.findByAssignToIsNullAndStatusIgnoreCase("available");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        if (assetRepo.existsById(id)) {
            assetRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/assign/{assetId}")
    public ResponseEntity<?> assignAsset(
            @PathVariable Long assetId,
            @RequestBody Map<String, Long> body
    ) {
        Long employeeId = body.get("employeeId");

        Optional<Asset> assetOpt = assetRepo.findById(assetId);
        Optional<Employee> empOpt = employeeRepo.findById(employeeId);

        if (assetOpt.isPresent() && empOpt.isPresent()) {
            Asset asset = assetOpt.get();
            asset.setAssignTo(empOpt.get());

            // ✅ Update status
            asset.setStatus("Assigned");

            assetRepo.save(asset);
            return ResponseEntity.ok(Map.of("message", "Asset assigned successfully."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid asset or employee ID."));
        }
    }

   @PutMapping("/{id}/status")
public ResponseEntity<Asset> updateAssetStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
    String newStatus = body.get("status");
    Optional<Asset> optionalAsset = assetRepo.findById(id);

    if (optionalAsset.isPresent()) {
        Asset asset = optionalAsset.get();
        asset.setStatus(newStatus);

        // ❗Unassign employee if returning or sending for maintenance
        if ("Available".equalsIgnoreCase(newStatus) || "Under Maintenance".equalsIgnoreCase(newStatus)) {
            asset.setAssignTo(null);
        }

        assetRepo.save(asset);
        return ResponseEntity.ok(asset);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    }

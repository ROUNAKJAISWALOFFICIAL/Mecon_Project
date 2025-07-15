package com.asset.asset_management.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.entities.Asset;
import com.asset.asset_management.entities.AssetCategory;
import com.asset.asset_management.entities.AssetRequest;
import com.asset.asset_management.entities.Employee;
import com.asset.asset_management.interfaces.AssetCategoryRepository;
import com.asset.asset_management.interfaces.AssetRepository;
import com.asset.asset_management.interfaces.AssetRequestRepository;
import com.asset.asset_management.interfaces.EmployeeRepository;

@RestController
@RequestMapping("/api/asset-requests")
public class AssetRequestController {

    @Autowired
    private AssetRequestRepository requestRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AssetCategoryRepository categoryRepo;

    @Autowired
    private AssetRepository assetRepo;

    //  Save asset request
    @PostMapping
    public AssetRequest requestAsset(@RequestBody RequestDTO dto) {
        Employee emp = employeeRepo.findById(dto.employeeId()).orElseThrow();
        AssetCategory cat = categoryRepo.findById(dto.categoryId()).orElseThrow();
        AssetRequest req = new AssetRequest();
        req.setEmployee(emp);
        req.setCategory(cat);
        req.setStatus("Pending");
        req.setRequestDate(LocalDateTime.now());

        return requestRepo.save(req);
    }

    //  Return mapped list for admin
    @GetMapping("/all")
    public List<Map<String, Object>> getAllRequestsForAdmin() {
        List<AssetRequest> requests = requestRepo.findAll();

        return requests.stream().map(req -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", req.getId());
            map.put("employeeId", req.getEmployee().getId());
            map.put("employeeName", req.getEmployee().getName());
            map.put("categoryId", req.getCategory().getId());
            map.put("categoryName", req.getCategory().getName());
            map.put("requestDate", req.getRequestDate());
            map.put("status", req.getStatus());
            return map;
        }).toList();
    }

    // Update status (Accepted / Rejected)
@PutMapping("/{id}/status")
public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
    Optional<AssetRequest> optional = requestRepo.findById(id);
    if (optional.isPresent()) {
        AssetRequest req = optional.get();
        String status = requestBody.get("status"); // read from JSON
        req.setStatus(status);
        requestRepo.save(req);
        return ResponseEntity.ok("Status updated to " + status);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
    }
}


    // Assign asset & approve request
    @PostMapping("/assign")
    public ResponseEntity<String> assignAssetToEmployee(@RequestBody AssignAssetRequest req) {
        Optional<AssetRequest> requestOpt = requestRepo.findById(req.getRequestId());
        if (requestOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }

        Optional<Asset> assetOpt = assetRepo.findById(req.getAssetId());
        if (assetOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset not found");
        }

        Optional<Employee> empOpt = employeeRepo.findById(req.getEmployeeId());
        if (empOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }

        // Assign asset to employee
        Asset asset = assetOpt.get();
        asset.setAssignTo(empOpt.get());
        asset.setStatus("Assigned");
        assetRepo.save(asset);

        // Update request status
        AssetRequest assetRequest = requestOpt.get();
        assetRequest.setStatus("Accepted");
        requestRepo.save(assetRequest);

        return ResponseEntity.ok("Asset assigned and request approved");
    }

    //  DTO for creating a request
    public record RequestDTO(Long employeeId, Long categoryId) {}

    //  DTO for approving request and assigning asset
    public static class AssignAssetRequest {
        private Long requestId;
        private Long employeeId;
        private Long assetId;

        public Long getRequestId() {
            return requestId;
        }

        public void setRequestId(Long requestId) {
            this.requestId = requestId;
        }

        public Long getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
        }

        public Long getAssetId() {
            return assetId;
        }

        public void setAssetId(Long assetId) {
            this.assetId = assetId;
        }
    }
}

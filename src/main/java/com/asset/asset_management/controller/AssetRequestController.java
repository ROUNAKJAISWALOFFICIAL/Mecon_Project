package com.asset.asset_management.controller;

import com.asset.asset_management.entities.AssetRequest;
import com.asset.asset_management.entities.Employee;
import com.asset.asset_management.entities.AssetCategory;
import com.asset.asset_management.interfaces.AssetRequestRepository;
import com.asset.asset_management.interfaces.EmployeeRepository;
import com.asset.asset_management.interfaces.AssetCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/asset-requests")
public class AssetRequestController {

    @Autowired
    private AssetRequestRepository requestRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AssetCategoryRepository categoryRepo;

    // ✅ 1. Save asset request (already correct)
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

    // ✅ 2. Return mapped list with readable fields for admin view
@GetMapping("/all")
public List<Map<String, Object>> getAllRequestsForAdmin() {
    List<AssetRequest> requests = requestRepo.findAll();

    return requests.stream().map(req -> {
        Map<String, Object> map = new HashMap<>();
        map.put("id", req.getId());
        map.put("employeeId", req.getEmployee().getId()); // 👈 ADD
        map.put("employeeName", req.getEmployee().getName());
        map.put("categoryId", req.getCategory().getId()); // 👈 ADD
        map.put("categoryName", req.getCategory().getName());
        map.put("requestDate", req.getRequestDate());
        map.put("status", req.getStatus());
        return map;
    }).toList();
}


    // ✅ 3. Update request status (Assign or Reject)
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<AssetRequest> optional = requestRepo.findById(id);
        if (optional.isPresent()) {
            AssetRequest req = optional.get();
            req.setStatus(status);
            requestRepo.save(req);
            return ResponseEntity.ok("Status updated to " + status);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
    }

    // ✅ DTO class for request input
    public record RequestDTO(Long employeeId, Long categoryId) {}
}

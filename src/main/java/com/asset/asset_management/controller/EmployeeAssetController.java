package com.asset.asset_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.model.AssetReport;
import com.asset.asset_management.service.ReportService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeAssetController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/{id}/assigned-assets")
    public List<AssetReport> getAssignedAssetsForEmployee(@PathVariable Long id) {
        return reportService.getAssetsAssignedToEmployee(id);
    }
}

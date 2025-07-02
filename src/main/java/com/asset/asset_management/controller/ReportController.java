package com.asset.asset_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.model.AssetReport;
import com.asset.asset_management.model.AssignmentHistory;
import com.asset.asset_management.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // This is the primary Asset Inventory Report endpoint
    @GetMapping("/asset-inventory")
    public ResponseEntity<List<AssetReport>> getAssetInventory(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status) { // status is String as per your entity
        List<AssetReport> report = reportService.getAssetInventoryReport(departmentId, categoryId, status);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/assignment-history")
    public ResponseEntity<List<AssignmentHistory>> getAssetAssignmentHistory(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long departmentId) {
        List<AssignmentHistory> report = reportService.getAssetAssignmentHistoryReport(employeeId, departmentId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/assets-by-department")
    public ResponseEntity<List<AssetReport>> getAssetsByDepartment(@RequestParam(required = false) Long departmentId) {
        List<AssetReport> report = reportService.getAssetsByDepartmentReport(departmentId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/assets-by-category")
    public ResponseEntity<List<AssetReport>> getAssetsByCategory(@RequestParam(required = false) Long categoryId) {
        List<AssetReport> report = reportService.getAssetsByCategoryReport(categoryId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/assets-by-status")
    public ResponseEntity<List<AssetReport>> getAssetsByStatus(@RequestParam(required = false) String status) {
        List<AssetReport> report = reportService.getAssetsByStatusReport(status);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/under-maintenance-assets")
    public ResponseEntity<List<AssetReport>> getUnderMaintenanceAssets() {
        List<AssetReport> report = reportService.getUnderMaintenanceAssetsReport();
        return ResponseEntity.ok(report);
    }

    // Add PDF/CSV generation endpoints here as described in previous response.
    // Ensure they call the appropriate `generate...Pdf` or `generate...Csv` methods in ReportService.
    // Example (if not already moved to MasterDataController's download endpoints):
    /*
    @GetMapping("/asset-inventory/pdf")
    public ResponseEntity<byte[]> downloadAssetInventoryPdf(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status) throws DocumentException, IOException {
        byte[] pdfBytes = reportService.generateAssetReportPdf(departmentId, categoryId, status);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "Asset_Inventory_Report.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
    */
}
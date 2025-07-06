package com.asset.asset_management.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.model.AssetCategoryDTO;
import com.asset.asset_management.model.AssetReport;
import com.asset.asset_management.model.DepartmentDTO;
import com.asset.asset_management.model.EmployeeDTO;
import com.asset.asset_management.model.EmployeeReportDTO;
import com.asset.asset_management.service.ReportService;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/api") // Keep this base mapping
@CrossOrigin(origins = "*")
public class MasterDataController {

    @Autowired
    private ReportService reportService;

    // --- Master Data Endpoints (for dropdowns) ---
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(reportService.getAllDepartments());
    }

    @GetMapping("/asset-categories")
    public ResponseEntity<List<AssetCategoryDTO>> getAllAssetCategories() {
        return ResponseEntity.ok(reportService.getAllAssetCategories());
    }

    @GetMapping("/masterdata/employees-list")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(reportService.getAllEmployees());
    }

    // --- REPORT DATA ENDPOINTS (for displaying reports in HTML table) ---
    // !!! CONFLICT RESOLVED HERE !!!
    // Changed path from "/reports/asset-inventory" to "/masterdata/asset-list"
    // This provides a general list of assets, distinct from the specific report in ReportController
    @GetMapping("/masterdata/asset-list") // <--- Changed this line!
    public ResponseEntity<List<AssetReport>> getAssetListForMasterData( // Renamed method for clarity
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status) {
        // This might fetch a simpler list, or if it's truly a "report" for master data,
        // it can still call the report service.
        List<AssetReport> report = reportService.getAssetInventoryReport(departmentId, categoryId, status);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/employee-details")
    public ResponseEntity<List<EmployeeReportDTO>> getEmployeeDetails(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate joinDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate joinDateEnd) {
        List<EmployeeReportDTO> report = reportService.getEmployeeDetailsReport(departmentId, joinDateStart, joinDateEnd);
        return ResponseEntity.ok(report);
    }


    // --- PDF DOWNLOAD ENDPOINTS (for generating filtered PDFs) ---
    // These paths are distinct from the HTML table report paths.
    // Updated Asset PDF Download
    @GetMapping("/downloads/assets/pdf") // Changed path to group downloads under /api/downloads/
    public ResponseEntity<byte[]> downloadAssetReportPdf(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status) {
        try {
            byte[] pdfBytes = reportService.generateAssetReportPdf(departmentId, categoryId, status);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "Asset_Inventory_Report_" + System.currentTimeMillis() + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // New Employee PDF Download
    @GetMapping("/downloads/employees/pdf") // Changed path to group downloads under /api/downloads/
    public ResponseEntity<byte[]> downloadEmployeeReportPdf(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate joinDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate joinDateEnd) {
        try {
            byte[] pdfBytes = reportService.generateEmployeeReportPdf(departmentId, joinDateStart, joinDateEnd);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "Employee_Details_Report_" + System.currentTimeMillis() + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
}
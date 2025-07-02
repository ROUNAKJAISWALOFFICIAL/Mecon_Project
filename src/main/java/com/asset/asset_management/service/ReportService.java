package com.asset.asset_management.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset.asset_management.entities.Asset;
import com.asset.asset_management.entities.Employee;
import com.asset.asset_management.interfaces.AssetCategoryRepository;
import com.asset.asset_management.interfaces.AssetRepository;
import com.asset.asset_management.interfaces.DepartmentRepository;
import com.asset.asset_management.interfaces.EmployeeRepository;
import com.asset.asset_management.model.AssetCategoryDTO;
import com.asset.asset_management.model.AssetReport;
import com.asset.asset_management.model.AssignmentHistory;
import com.asset.asset_management.model.DepartmentDTO;
import com.asset.asset_management.model.EmployeeDTO;
import com.asset.asset_management.model.EmployeeReportDTO;

// iText PDF Library Imports
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;

@Service
public class ReportService {

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private AssetCategoryRepository assetCategoryRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<AssetReport> getAssetInventoryReport(Long departmentId, Long categoryId, String status) {
        List<Asset> assets;

        String effectiveStatus = (status != null && status.isEmpty()) ? null : status;

        if (departmentId != null && categoryId != null && effectiveStatus != null) {
            assets = assetRepository.findByAssignTo_Department_IdAndCategory_IdAndStatusIgnoreCase(departmentId, categoryId, effectiveStatus);
        } else if (departmentId != null && categoryId != null) {
            assets = assetRepository.findByAssignTo_Department_IdAndCategory_Id(departmentId, categoryId);
        } else if (departmentId != null && effectiveStatus != null) {
            assets = assetRepository.findByAssignTo_Department_IdAndStatusIgnoreCase(departmentId, effectiveStatus);
        } else if (categoryId != null && effectiveStatus != null) {
            assets = assetRepository.findByCategory_IdAndStatusIgnoreCase(categoryId, effectiveStatus);
        } else if (departmentId != null) {
            assets = assetRepository.findByAssignTo_Department_Id(departmentId);
        } else if (categoryId != null) {
            assets = assetRepository.findByCategory_Id(categoryId);
        } else if (effectiveStatus != null) {
            assets = assetRepository.findByStatusIgnoreCase(effectiveStatus);
        } else {
            assets = assetRepository.findAll();
        }

        return assets.stream()
                     .map(AssetReport::new)
                     .collect(Collectors.toList());
    }

    public List<AssignmentHistory> getAssetAssignmentHistoryReport(Long employeeId, Long departmentId) {
        List<Asset> assets;
        if (employeeId != null) {
            assets = assetRepository.findByAssignTo_Id(employeeId);
        } else if (departmentId != null) {
            assets = assetRepository.findByAssignTo_Department_Id(departmentId);
        } else {
            assets = assetRepository.findByAssignToIsNotNull();
        }
        return assets.stream()
                     .map(AssignmentHistory::new)
                     .collect(Collectors.toList());
    }

    public List<AssetReport> getAssetsByDepartmentReport(Long departmentId) {
        List<Asset> assets = (departmentId != null) ?
                assetRepository.findByAssignTo_Department_Id(departmentId) :
                assetRepository.findByAssignToIsNotNull();
        return assets.stream().map(AssetReport::new).collect(Collectors.toList());
    }

    public List<AssetReport> getAssetsByCategoryReport(Long categoryId) {
        List<Asset> assets = (categoryId != null) ? assetRepository.findByCategory_Id(categoryId) : assetRepository.findAll();
        return assets.stream().map(AssetReport::new).collect(Collectors.toList());
    }

    public List<AssetReport> getAssetsByStatusReport(String status) {
        List<Asset> assets = (status != null && !status.isEmpty()) ? assetRepository.findByStatusIgnoreCase(status) : assetRepository.findAll();
        return assets.stream().map(AssetReport::new).collect(Collectors.toList());
    }

    public List<AssetReport> getUnderMaintenanceAssetsReport() {
        List<Asset> assets = assetRepository.findByStatusIgnoreCase("UNDER_MAINTENANCE");
        return assets.stream().map(AssetReport::new).collect(Collectors.toList());
    }

    // --- Corrected Method Call Here ---
    public List<EmployeeReportDTO> getEmployeeDetailsReport(Long departmentId, LocalDate joinDateStart, LocalDate joinDateEnd) {
        List<Employee> employees;

        if (departmentId != null && joinDateStart != null && joinDateEnd != null) {
            employees = employeeRepository.findByDepartment_IdAndJoinDateBetween(departmentId, joinDateStart, joinDateEnd);
        } else if (departmentId != null) {
            // This line was calling findByDepartmentId, changed to findByDepartment_Id
            employees = employeeRepository.findByDepartment_Id(departmentId);
        } else if (joinDateStart != null && joinDateEnd != null) {
            employees = employeeRepository.findByJoinDateBetween(joinDateStart, joinDateEnd);
        } else {
            employees = employeeRepository.findAll();
        }

        return employees.stream()
                        .map(EmployeeReportDTO::new)
                        .collect(Collectors.toList());
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentDTO::new)
                .collect(Collectors.toList());
    }

    public List<AssetCategoryDTO> getAllAssetCategories() {
        return assetCategoryRepository.findAll().stream()
                .map(AssetCategoryDTO::new)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }

    public byte[] generateAssetReportPdf(Long departmentId, Long categoryId, String status) throws DocumentException, IOException {
        List<AssetReport> reportData = getAssetInventoryReport(departmentId, categoryId, status);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Asset Management System - Asset Inventory Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        StringBuilder filterInfo = new StringBuilder("Filters: ");
        if (departmentId != null) filterInfo.append("Department ID: ").append(departmentRepository.findById(departmentId).map(d -> d.getDepartmentName()).orElse("N/A")).append("; ");
        if (categoryId != null) filterInfo.append("Category ID: ").append(assetCategoryRepository.findById(categoryId).map(c -> c.getName()).orElse("N/A")).append("; ");
        if (status != null && !status.isEmpty()) filterInfo.append("Status: ").append(status).append("; ");
        if (filterInfo.toString().equals("Filters: ")) {
            filterInfo.append("None (All Assets)");
        }
        Paragraph filterParagraph = new Paragraph(filterInfo.toString(), FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY));
        filterParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        filterParagraph.setSpacingAfter(20);
        document.add(filterParagraph);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        float[] columnWidths = {0.8f, 1.5f, 2.0f, 1.0f, 1.2f, 1.5f, 1.5f, 1.5f};
        table.setWidths(columnWidths);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK);

        String[] headers = {"ID", "Name", "Description", "Value", "Status", "Category", "Assigned To", "Department"};
        for (String header : headers) {
            PdfPCell hCell = new PdfPCell(new Phrase(header, headerFont));
            hCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            hCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            hCell.setBackgroundColor(new BaseColor(50, 100, 150));
            hCell.setPadding(5);
            table.addCell(hCell);
        }

        for (AssetReport assetReport : reportData) {
            table.addCell(new PdfPCell(new Phrase(String.valueOf(assetReport.getId()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(assetReport.getName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(assetReport.getDescription(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f", assetReport.getValue()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(assetReport.getStatus(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(assetReport.getCategoryName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(assetReport.getAssignedToEmployeeName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(assetReport.getDepartmentName(), cellFont)));
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }

    public byte[] generateEmployeeReportPdf(Long departmentId, LocalDate joinDateStart, LocalDate joinDateEnd) throws DocumentException, IOException {
        List<EmployeeReportDTO> reportData = getEmployeeDetailsReport(departmentId, joinDateStart, joinDateEnd);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Asset Management System - Employee Details Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        StringBuilder filterInfo = new StringBuilder("Filters: ");
        if (departmentId != null) filterInfo.append("Department: ").append(departmentRepository.findById(departmentId).map(d -> d.getDepartmentName()).orElse("N/A")).append("; ");
        if (joinDateStart != null) filterInfo.append("Join Date From: ").append(joinDateStart).append("; ");
        if (joinDateEnd != null) filterInfo.append("Join Date To: ").append(joinDateEnd).append("; ");
        if (filterInfo.toString().equals("Filters: ")) {
            filterInfo.append("None (All Employees)");
        }
        Paragraph filterParagraph = new Paragraph(filterInfo.toString(), FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY));
        filterParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        filterParagraph.setSpacingAfter(20);
        document.add(filterParagraph);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        float[] columnWidths = {0.8f, 1.5f, 1.5f, 2.0f, 1.5f, 1.2f, 1.5f};
        table.setWidths(columnWidths);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK);

        String[] headers = {"ID", "Name", "Designation", "Email", "Phone", "Join Date", "Department"};
        for (String header : headers) {
            PdfPCell hCell = new PdfPCell(new Phrase(header, headerFont));
            hCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            hCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            hCell.setBackgroundColor(new BaseColor(50, 100, 150));
            hCell.setPadding(5);
            table.addCell(hCell);
        }

        for (EmployeeReportDTO employeeReport : reportData) {
            table.addCell(new PdfPCell(new Phrase(String.valueOf(employeeReport.getId()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(employeeReport.getName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(employeeReport.getDesignation(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(employeeReport.getEmail(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(employeeReport.getPhone(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(employeeReport.getJoinDate() != null ? employeeReport.getJoinDate().toString() : "N/A", cellFont)));
            table.addCell(new PdfPCell(new Phrase(employeeReport.getDepartmentName(), cellFont)));
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }
}
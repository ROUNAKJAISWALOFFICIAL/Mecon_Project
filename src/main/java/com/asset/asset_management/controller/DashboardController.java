package com.asset.asset_management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.entities.Asset;
import com.asset.asset_management.entities.AssetCategory;
import com.asset.asset_management.entities.Department;
import com.asset.asset_management.entities.Employee;
import com.asset.asset_management.interfaces.AssetCategoryRepository;
import com.asset.asset_management.interfaces.AssetRepository;
import com.asset.asset_management.interfaces.DepartmentRepository;
import com.asset.asset_management.interfaces.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private AssetRepository assetRepo;

    @Autowired
    private AssetCategoryRepository categoryRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private DepartmentRepository departmentRepo;

    // Dashboard summary
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();

        long assetCount = assetRepo.count();
        long employeeCount = employeeRepo.count();
        long departmentCount = departmentRepo.count();
        long categoryCount = categoryRepo.count();
        List<Asset> latestAssets = assetRepo.findTop5ByOrderByIdDesc();

        System.out.println("Asset Count: " + assetCount);
        System.out.println("Category Count: " + categoryCount);
        System.out.println("Latest Assets: " + latestAssets.size());

        data.put("assetCount", assetCount);
        data.put("employeeCount", employeeCount);
        data.put("departmentCount", departmentCount);
        data.put("categoryCount", categoryCount);
        data.put("latestAssets", latestAssets);
        return data;
    }
@GetMapping("/assets")
public List<Asset> getAssets() {
    return assetRepo.findAll();
}

    // ✅ Added: Fetch all asset categories
    @GetMapping("/asset-categories")
    public List<AssetCategory> getAssetCategories() {
        return categoryRepo.findAll();
    }

    // ✅ Added: Fetch all departments
    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return departmentRepo.findAll();
    }

    // ✅ Added: Fetch all employees
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeRepo.findAll();
    }
}

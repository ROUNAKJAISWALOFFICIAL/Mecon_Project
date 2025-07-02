package com.asset.asset_management.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping; // Added for completeness if you have delete
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; // Added for completeness if you have update
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.entities.Department;
import com.asset.asset_management.interfaces.DepartmentRepository;

@RestController
@RequestMapping("/api/departments") // This is the base path for Department-specific operations
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // *** IMPORTANT: COMMENT OUT OR REMOVE THIS METHOD ***
    // This method is causing the conflict. The MasterDataController will handle
    // the general GET /api/departments for lookup data.
    /*
    @GetMapping // This would map to GET /api/departments, conflicting with MasterDataController
    public List<Department> getDepartments() { // Or whatever your method name is
        return departmentRepository.findAll();
    }
    */
    // ***************************************************

    // --- You can keep or add other specific Department CRUD operations here ---

    @GetMapping("/{id}") // Example: Get a department by ID
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping // Example: Create a new department
    public Department addDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @PutMapping("/{id}") // Example: Update an existing department
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        return departmentRepository.findById(id)
            .map(department -> {
                department.setDepartmentName(departmentDetails.getDepartmentName());
                // Set other fields as necessary
                return ResponseEntity.ok(departmentRepository.save(department));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}") // Example: Delete a department
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
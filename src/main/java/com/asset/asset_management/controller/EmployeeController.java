package com.asset.asset_management.controller;

import com.asset.asset_management.entities.Employee;
import com.asset.asset_management.interfaces.EmployeeRepository; // Assuming this is your repository package
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees") // Base path for all employee-related endpoints
@CrossOrigin(origins = "*") // Allow cross-origin requests from any domain (for development)
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // 1. Get all employees
    // GET /api/employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // 2. Get employee by ID
    // GET /api/employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok) // If employee found, return OK with employee
                .orElseGet(() -> ResponseEntity.notFound().build()); // Else return 404 Not Found
    }

    // 3. Create a new employee
    // POST /api/employees
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Return 201 Created status
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // 4. Update an existing employee
    // PUT /api/employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(employeeDetails.getName());
                    employee.setDesignation(employeeDetails.getDesignation());
                    employee.setEmail(employeeDetails.getEmail());
                    employee.setPhone(employeeDetails.getPhone());
                    employee.setJoinDate(employeeDetails.getJoinDate());
                    // Update department if it's part of the Employee entity and present in employeeDetails
                    if (employeeDetails.getDepartment() != null) {
                        employee.setDepartment(employeeDetails.getDepartment());
                    }
                    Employee updatedEmployee = employeeRepository.save(employee);
                    return ResponseEntity.ok(updatedEmployee);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5. Delete an employee
    // DELETE /api/employees/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Return 204 No Content status
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // 6. Get employees by Department ID (the method that caused the error)
    // GET /api/employees/department/{id}
    @GetMapping("/department/{id}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable("id") Long departmentId) {
        // CORRECTED LINE: Using findByDepartment_Id as defined in your EmployeeRepository
        List<Employee> employees = employeeRepository.findByDepartment_Id(departmentId);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if list is empty
        }
        return ResponseEntity.ok(employees);
    }

    // 7. Get employees by join date range
    // GET /api/employees/by-join-date?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
    @GetMapping("/by-join-date")
    public ResponseEntity<List<Employee>> getEmployeesByJoinDateBetween(
            @RequestParam("startDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate startDate,
            @RequestParam("endDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate endDate) {
        List<Employee> employees = employeeRepository.findByJoinDateBetween(startDate, endDate);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    // 8. Get employees by department ID and join date range
    // GET /api/employees/by-department-and-join-date?departmentId=1&startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
    @GetMapping("/by-department-and-join-date")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentAndJoinDateBetween(
            @RequestParam("departmentId") Long departmentId,
            @RequestParam("startDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate startDate,
            @RequestParam("endDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate endDate) {
        List<Employee> employees = employeeRepository.findByDepartment_IdAndJoinDateBetween(departmentId, startDate, endDate);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }
}
package com.asset.asset_management.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.entities.Employee;
import com.asset.asset_management.interfaces.EmployeeRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*") // For frontend access (adjust in production)
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // 1. Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // 2. Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 3. Get currently logged-in employee using session
@GetMapping("/current")
public ResponseEntity<?> getCurrentLoggedInEmployee(HttpSession session) {
    Long employeeId = (Long) session.getAttribute("employeeId");

    if (employeeId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Not logged in as employee");
    }

    Optional<Employee> empOpt = employeeRepository.findById(employeeId);

    if (empOpt.isPresent()) {
        return ResponseEntity.ok(empOpt.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Employee not found");
    }
}

    // 4. Create a new employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // 5. Update an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(employeeDetails.getName());
                    employee.setDesignation(employeeDetails.getDesignation());
                    employee.setEmail(employeeDetails.getEmail());
                    employee.setPhone(employeeDetails.getPhone());
                    employee.setJoinDate(employeeDetails.getJoinDate());
                    if (employeeDetails.getDepartment() != null) {
                        employee.setDepartment(employeeDetails.getDepartment());
                    }
                    Employee updatedEmployee = employeeRepository.save(employee);
                    return ResponseEntity.ok(updatedEmployee);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 6. Delete an employee
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 7. Get employees by Department ID
    @GetMapping("/department/{id}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable("id") Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartment_Id(departmentId);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    // 8. Get employees by join date range
    @GetMapping("/by-join-date")
    public ResponseEntity<List<Employee>> getEmployeesByJoinDateBetween(
            @RequestParam("startDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Employee> employees = employeeRepository.findByJoinDateBetween(startDate, endDate);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    // 9. Get employees by department and join date range
    @GetMapping("/by-department-and-join-date")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentAndJoinDateBetween(
            @RequestParam("departmentId") Long departmentId,
            @RequestParam("startDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Employee> employees = employeeRepository.findByDepartment_IdAndJoinDateBetween(departmentId, startDate, endDate);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }
}

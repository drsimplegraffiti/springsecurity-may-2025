package com.drsimple.jwtsecurity.employee;


import com.drsimple.jwtsecurity.util.CurrentUserUtil;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CurrentUserUtil currentUserUtil;

    public EmployeeService(EmployeeRepository employeeRepository, CurrentUserUtil currentUserUtil) {
        this.employeeRepository = employeeRepository;
        this.currentUserUtil = currentUserUtil;
    }

    // Save a new employee
    public Employee saveEmployee(EmployeeRequest<Employee> request) {
        System.out.println("Current User: " + currentUserUtil.getLoggedInUser().getUsername());
        Employee employee = request.getData();
        // You could perform additional validation or enrich the employee data here
        return employeeRepository.save(employee);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get a single employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // Update an employee
    public Employee updateEmployee(Long id, EmployeeRequest<Employee> request) {
        Employee existing = getEmployeeById(id);
        Employee updatedData = request.getData();

        existing.setName(updatedData.getName());
        existing.setDateOfBirth(updatedData.getDateOfBirth());
        existing.setDepartment(updatedData.getDepartment());
        existing.setSalary(updatedData.getSalary());

        return employeeRepository.save(existing);
    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
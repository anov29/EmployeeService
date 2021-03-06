package com.employee.web.controller;

import java.util.HashMap;

import com.employee.web.model.Employee;
import com.employee.web.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rest controller for employee service
 */
@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    // Dependency injection of Employee Service
    // Spring Beans are by default Singletons,
    // and so this uses the Singletons design pattern
    // to not recreate instances of the employeeService across the Spring IoC container
    // Also by injecting an interface we follow the Dependency Inversion Principle
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Get all active employees
     * @return Hashmap of <Employee ID><Active Employee>
     */
    @GetMapping("/employees")
    public ResponseEntity<HashMap<Long, Employee>> getEmployees() {
        try {
            return new ResponseEntity<>(employeeService.getActiveEmployees(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error in getEmployees: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get active employee by ID
     * @param id id of employee
     * @return employee with corresponding id
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) {
        try {
            if (!employeeService.getActiveEmployees().containsKey(id)) {
                LOGGER.info("Server retrieved bad request id {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(employeeService.getActiveEmployees().get(id), HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Error in getEmployee: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new employee
     * @param newEmployee new employee to be created
     * @return if employee creation was successful
     */
    @PostMapping("/employees")
    public ResponseEntity<String> newEmployee(@RequestBody Employee newEmployee) {
        try {
            if (missingFields(newEmployee)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields {FirstName, LastName, DateOfBirth, DateOfEmployment} cannot be null.");
            }
            if (employeeService.hasEmployee(newEmployee)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + newEmployee.getId() +
                        " already exists. Please use a unique ID or use a PUT request to update an employee");
            }

            boolean status = employeeService.createEmployee(newEmployee);
            if (!status) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create new employee with id " + newEmployee.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body("Successfully created employee " + newEmployee.getId());
        } catch (Exception e) {
            LOGGER.error("Exception in newEmployee creation ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception creating newEmployee with id " + newEmployee.getId());
        }
    }

    /**
     * Update an existing employee
     * @param employee employee with updated fields
     * @return if employee update was successful
     */
    @PutMapping("/employees")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) {
        try {
            if (missingFields(employee)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields {FirstName, LastName, DateOfBirth, DateOfEmployment} cannot be null.");
            }
            if (!employeeService.hasEmployee(employee)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + employee.getId() +
                        " does not exist. Please use a existing ID or use a POST request to create an employee");
            }
            if (employeeService.getActiveEmployees().containsKey(employee.getId()) && employee.getStatus() == Employee.State.INACTIVE) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PUT request cannot make employee inactive. Please use delete request instead");
            }

            boolean status = employeeService.updateEmployee(employee);
            if (!status) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create new employee with id " + employee.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated employee ID" + employee.getId());
        } catch (Exception e) {
            LOGGER.error("Exception in updateEmployee ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception updating employee with id " + employee.getId());

        }
    }

    /**
     * Delete an employee
     * @param id id of employee to delete
     * @return if employee with corresponding id was deleted
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            if (!employeeService.getActiveEmployees().containsKey(id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No active employee with id " + id);
            }

            boolean status = employeeService.deleteEmployee(id);
            if (!status) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete employee with id " + id);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted employee with ID: " + id);

        } catch (Exception e) {
            LOGGER.error("Exception in deleteEmployee ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception deleting employee with id " + id);
        }
    }

    // returns true if employee missing required fields
    private boolean missingFields(Employee e) {
        // Status can be null, and will be made ACTIVE by default
        // no id will be deserialized to 0 by jackson
        return (e.getFirstName() == null || e.getLastName() == null || e.getDateOfEmployment() == null
                || e.getDateOfBirth() == null);
    }
}
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

@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<HashMap<Long, Employee>> getEmployees() {
        try {
            return new ResponseEntity<>(employeeService.getActiveEmployees(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error in getEmployees: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @PostMapping("/employees")
    public ResponseEntity<String> newEmployee(@RequestBody Employee newEmployee) {
        try {
            // validation
            // note that no id will be deserialized to 0 by jackson
            if (newEmployee.getFirstName() == null || newEmployee.getLastName() == null || newEmployee.getDateOfEmployment() == null
                || newEmployee.getDateOfBirth() == null || newEmployee.getStatus() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields {FirstName, LastName, DateOfBirth, DateOfEmployment, Status} cannot be null.");
            }
            if (employeeService.getActiveEmployees().containsKey(newEmployee.getId()) || employeeService.getInactiveEmployees().containsKey(newEmployee.getId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + newEmployee.getId() +
                        " already exists. Please use a unique ID or use a PUT request to update an employee");
            }

            boolean status = employeeService.createEmployee(newEmployee);
            if (!status) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create new employee with id " + newEmployee.getId());
        } catch (Exception e) {
            LOGGER.error("Exception in newEmployee creation ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception creating newEmployee.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully created employee " + newEmployee.getId());
    }
}


// curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"id": 5, "firstName": "Samwise", "middleInitial": "S", "lastName":"Nice", "dateOfBirth":"2018-04-01T07:30:00.000+00:00", "dateOfEmployment": "2018-04-01T07:30:00.000+00:00", "status": "ACTIVE"}'
// curl -v localhost:8080/employees/5
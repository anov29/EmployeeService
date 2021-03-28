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
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(employeeService.getActiveEmployees().get(id), HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Error in getEmployee: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
package com.employee.web.services;

import com.employee.web.model.Employee;

import java.util.HashMap;

/**
 * Service for working with Employee model
 */
public interface EmployeeService {

    HashMap<Long, Employee> getActiveEmployees();

    HashMap<Long, Employee> getInactiveEmployees();

    boolean hasEmployee(Employee e);

    boolean deleteEmployee(Long id);

    boolean createEmployee(Employee e);

    boolean updateEmployee(Employee e);

}

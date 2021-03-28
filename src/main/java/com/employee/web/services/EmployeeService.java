package com.employee.web.services;

import com.employee.web.model.Employee;

import java.util.HashMap;

public interface EmployeeService {
    HashMap<Long, Employee> getActiveEmployees();

    HashMap<Long, Employee> getInactiveEmployees();

    void deleteEmployee(Employee e);

    boolean createEmployee(Employee e);
}

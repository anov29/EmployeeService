package com.employee.web.services;

import com.employee.web.model.Employee;

import java.util.HashMap;

public interface EmployeeService {

    HashMap<Long, Employee> getActiveEmployees();

    HashMap<Long, Employee> getInactiveEmployees();

    boolean hasEmployee(Employee e);

    void deleteEmployee(Employee e);

    boolean createEmployee(Employee e);

    boolean updateEmployee(Employee e);
}

package com.employee.web.services;

import com.employee.web.model.Employee;
import com.employee.web.model.User;

import java.util.HashMap;

/**
 * Service for loading JSON configurations
 */
public interface JSONLoaderService {

    HashMap<Long, Employee> getLoadedActiveEmployees();

    HashMap<Long, Employee> getLoadedInactiveEmployees();

    User getLoadedUser();

}

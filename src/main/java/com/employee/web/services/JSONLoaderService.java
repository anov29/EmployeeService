package com.employee.web.services;

import com.employee.web.model.Employee;
import com.employee.web.model.User;

import java.util.HashMap;

/**
 * Service for loading JSON configurations
 * Following the Single Responsibility principle, this interface makes sure
 * other services don't have to implement a resource loader
 */
public interface JSONLoaderService {

    HashMap<Long, Employee> getLoadedActiveEmployees();

    HashMap<Long, Employee> getLoadedInactiveEmployees();

    User getLoadedUser();

}

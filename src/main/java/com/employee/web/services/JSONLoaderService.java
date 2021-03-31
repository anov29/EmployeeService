package com.employee.web.services;

import com.employee.web.model.Employee;
import com.employee.web.model.User;

import java.util.HashMap;

public interface JSONLoaderService {

    HashMap<Long, Employee> getLoadedActiveEmployees();

    HashMap<Long, Employee> getLoadedInactiveEmployees();

    User[] getLoadedUsers();

}

package com.employee.web.services;

import com.employee.web.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private HashMap<Long, Employee> activeEmployees = new HashMap<>();
    private HashMap<Long, Employee> inactiveEmployees = new HashMap<>();

    private JSONLoaderService jsonLoaderService;

    @Autowired
    public void setJsonLoaderService(JSONLoaderService jsonLoaderService) {
        this.jsonLoaderService = jsonLoaderService;
    }

    @PostConstruct
    public void loadJSON() {
        activeEmployees = jsonLoaderService.getLoadedActiveEmployees();
        inactiveEmployees = jsonLoaderService.getLoadedInactiveEmployees();
    }

    @Override
    public HashMap<Long, Employee> getActiveEmployees() {
        return activeEmployees;
    }

    @Override
    public HashMap<Long, Employee> getInactiveEmployees() {
        return inactiveEmployees;
    }

    @Override
    public boolean hasEmployee(Employee e) { return (activeEmployees.containsKey(e.getId()) || inactiveEmployees.containsKey(e.getId())); }

    @Override
    public boolean deleteEmployee(Long id) {
        try {
            Employee e = activeEmployees.get(id);
            activeEmployees.remove(id);
            inactiveEmployees.put(e.getId(), e);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error deleting employee " + id);
            return false;
        }
    }

    @Override
    public boolean createEmployee(Employee e) {
        try {
            if (e.getStatus() == Employee.State.ACTIVE) {
                activeEmployees.put(e.getId(), e);
            } else {
                inactiveEmployees.put(e.getId(), e);
            }
            return true;
        } catch (Exception ex) {
            LOGGER.error("Error adding employee ", ex);
            return false;
        }
    }

    @Override
    public boolean updateEmployee(Employee e) {
        try {
            activeEmployees.remove(e.getId());
            inactiveEmployees.remove(e.getId());

            if (e.getStatus() == Employee.State.ACTIVE) {
                activeEmployees.put(e.getId(), e);
            } else {
                inactiveEmployees.put(e.getId(), e);
            }
            return true;
        } catch (Exception ex) {
            LOGGER.error("Error updating employee ", ex);
            return false;
        }
    }
}

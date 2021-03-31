package com.employee.web.services;

import com.employee.web.model.Employee;
import com.employee.web.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class JSONLoaderServiceImpl implements JSONLoaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONLoaderServiceImpl.class);

    private final HashMap<Long, Employee> activeEmployees = new HashMap<>();
    private final HashMap<Long, Employee> inactiveEmployees = new HashMap<>();

    @PostConstruct
    public void loadConfigs() {
        // read json config for employees
        try {
            ObjectMapper mapper = new ObjectMapper();
            String fileName = "employees.json";
            File file = ResourceUtils.getFile("classpath:" + fileName);
            Employee[] employees = mapper.readValue(file, Employee[].class);

            // create an active and inactive employee map
            for (Employee e : employees) {
                LOGGER.debug(e.getFirstName());
                if (e.getStatus() == Employee.State.ACTIVE) {
                    activeEmployees.put(e.getId(), e);
                } else {
                    inactiveEmployees.put(e.getId(), e);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<Long, Employee> getLoadedActiveEmployees() {
        return activeEmployees;
    }

    @Override
    public HashMap<Long, Employee> getLoadedInactiveEmployees() {
        return inactiveEmployees;
    }

    @Override
    public User[] getLoadedUsers() {
        return new User[0];
    }
}
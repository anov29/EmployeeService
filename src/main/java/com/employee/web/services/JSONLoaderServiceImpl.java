package com.employee.web.services;

import com.employee.web.model.Employee;
import com.employee.web.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


/**
 * Service for loading JSON configurations
 */
@Service
public class JSONLoaderServiceImpl implements JSONLoaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONLoaderServiceImpl.class);

    private final HashMap<Long, Employee> activeEmployees = new HashMap<>();
    private final HashMap<Long, Employee> inactiveEmployees = new HashMap<>();
    private User user;

    @PostConstruct
    public void loadConfigs() {
        try {
            // read json config for employees
            ObjectMapper mapper = new ObjectMapper();

            InputStream is = new ClassPathResource("employees.json").getInputStream();
            Employee[] employees = mapper.readValue(is, Employee[].class);

            // create an active and inactive employee map
            for (Employee e : employees) {
                LOGGER.debug(e.getFirstName());
                if (e.getStatus() == Employee.State.ACTIVE) {
                    activeEmployees.put(e.getId(), e);
                } else {
                    inactiveEmployees.put(e.getId(), e);
                }
            }

            // read json config for authenticated user
            is = new ClassPathResource("user.json").getInputStream();
            this.user = mapper.readValue(is, User.class);

        } catch (IOException e) {
            LOGGER.error("Error reading JSON files: ", e);
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
    public User getLoadedUser() { return user; }
}

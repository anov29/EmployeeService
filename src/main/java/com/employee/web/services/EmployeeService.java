package com.employee.web.services;

import com.employee.web.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class EmployeeService {

    private HashMap<Integer, Employee> activeEmployees;
    private HashMap<Integer, Employee> inactiveEmployees;

    public EmployeeService() {

        // read json config
        try {
            ObjectMapper mapper = new ObjectMapper();
            String fileName = "employees.json";
            File file = ResourceUtils.getFile("classpath:" + fileName);
            Employee[] employees = mapper.readValue(file, Employee[].class);

            for (Employee e : employees) {
                System.out.println(e.getFirstName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.employee.employeeservice.services;

import com.employee.employeeservice.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

public class EmployeeService {

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

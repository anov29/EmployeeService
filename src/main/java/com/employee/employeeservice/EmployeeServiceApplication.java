package com.employee.employeeservice;

import com.employee.employeeservice.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class EmployeeServiceApplication {

	public static void main(String[] args) {

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

		SpringApplication.run(EmployeeServiceApplication.class, args);


	}

}

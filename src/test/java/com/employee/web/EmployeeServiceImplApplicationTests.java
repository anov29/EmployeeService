package com.employee.web;

import com.employee.web.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests application boot
 */
@SpringBootTest
class EmployeeServiceImplApplicationTests {

	@Autowired
	private EmployeeController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}

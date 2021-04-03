package com.employee.web;

import com.employee.web.model.User;
import com.employee.web.services.JSONLoaderService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.employee.web.model.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HTTPRequestsTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPRequestsTests.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final HttpHeaders headers = new HttpHeaders();
    private HashMap<Long, Employee> activeEmployees;
    private User authUser;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JSONLoaderService jsonLoaderService;

    @BeforeAll
    public static void setup() {
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    // init JSON loading, cannot be in BeforeAll as jsonLoaderService cannot be static
    @BeforeEach
    public void initJSON() {
        activeEmployees = jsonLoaderService.getLoadedActiveEmployees();
        authUser = jsonLoaderService.getLoadedUser();
    }

    @Test
    public void testGetEmployees() throws Exception {
        ParameterizedTypeReference<HashMap<Long, Employee>>  parameterizedTypeReference = new ParameterizedTypeReference<HashMap<Long, Employee>>(){};
        ResponseEntity<HashMap<Long, Employee>> exchange =
                restTemplate.exchange("http://localhost:" + port + "/employees", HttpMethod.GET, null, parameterizedTypeReference);
        HashMap<Long, Employee> employees = exchange.getBody();

        assert employees != null;
        assertThat(employees.equals(activeEmployees)).isTrue();
    }

    @Test
    public void testGetEmployee() {
        Map.Entry<Long, Employee> entry = activeEmployees.entrySet().iterator().next();
        Employee testEmployee = entry.getValue(); // get random employee

        Employee employee = getEmployee(testEmployee.getId());
        assert employee != null;
        assertThat((employee.equals(testEmployee))).isTrue();
    }

    @Test
    public void testPostEmployee() throws ParseException {
        JSONObject employeeJSON = new JSONObject();

        long id = 1099005;
        Employee testEmployee = new Employee(id, "Samwise", "S", "Nice",
                formatter.parse("1999-05-12"), formatter.parse("2019-1-22"),
                "ACTIVE");
        employeeJSON.put("id", testEmployee.getId());
        employeeJSON.put("firstName", testEmployee.getFirstName());
        employeeJSON.put("middleInitial", testEmployee.getMiddleInitial());
        employeeJSON.put("lastName", testEmployee.getLastName());
        employeeJSON.put("dateOfBirth", formatter.format(testEmployee.getDateOfBirth()));
        employeeJSON.put("dateOfEmployment", formatter.format(testEmployee.getDateOfEmployment()));
        employeeJSON.put("status", testEmployee.getStatus());

        HttpEntity<String> request =
                new HttpEntity<String>(employeeJSON.toString(), headers);

        String result =
                restTemplate.postForObject("http://localhost:" + port + "/employees", request, String.class);
        assert result != null;
        assertThat(result.equals("Successfully created employee " + testEmployee.getId())).isTrue();

        Employee storedEmployee = getEmployee(testEmployee.getId());
        assertThat(storedEmployee.equals(testEmployee)).isTrue();
    }

    @Test
    public void testPutEmployees() throws ParseException {
        Long employeeID = activeEmployees.keySet().iterator().next(); // get preexisting employee id
        Employee testEmployee = new Employee(employeeID, "Samwise", "S", "Nice",
                formatter.parse("1999-05-12"), formatter.parse("2019-1-22"),
                "ACTIVE");

        JSONObject employeeJSON = new JSONObject();
        employeeJSON.put("id", testEmployee.getId());
        employeeJSON.put("firstName", testEmployee.getFirstName());
        employeeJSON.put("middleInitial", testEmployee.getMiddleInitial());
        employeeJSON.put("lastName", testEmployee.getLastName());
        employeeJSON.put("dateOfBirth", formatter.format(testEmployee.getDateOfBirth()));
        employeeJSON.put("dateOfEmployment", formatter.format(testEmployee.getDateOfEmployment()));
        employeeJSON.put("status", testEmployee.getStatus());

        HttpEntity<String> request =
                new HttpEntity<String>(employeeJSON.toString(), headers);

        HttpEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/employees", HttpMethod.PUT, request, String.class);
        assert response.getBody() != null;
        assertThat(response.getBody().equals("Successfully updated employee ID" + testEmployee.getId())).isTrue();

        Employee storedEmployee = getEmployee(testEmployee.getId());
        assertThat(storedEmployee.equals(testEmployee)).isTrue();
    }

    @Test
    public void testDeleteEmployeeNoAuth() {
        Long employeeID = activeEmployees.keySet().iterator().next(); // get preexisting employee id

        ResponseEntity<String> response =
                restTemplate.exchange("http://localhost:" + port + "/employees/" +employeeID, HttpMethod.DELETE, null, String.class);

        assert response.getBody() != null;
        assertThat(response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)).isTrue();
    }

    @Test
    public void testDeleteEmployeeWithAuth() {
        Long employeeID = activeEmployees.keySet().iterator().next(); // get preexisting employee id

        headers.setBasicAuth(authUser.getUsername(), "1234G"); // have to use plaintext password here at authUser passwords is hashed
        HttpEntity<String> request =
                new HttpEntity<String>(null, headers);


        ResponseEntity<String> response =
                restTemplate.exchange("http://localhost:" + port + "/employees/" +employeeID, HttpMethod.DELETE, request, String.class);
        assert response.getBody() != null;

        assertThat(response.getBody().equals("Successfully deleted employee with ID: " + employeeID)).isTrue();

        assertThat(activeEmployees.containsKey(employeeID)).isFalse();
    }


    private Employee getEmployee(long id) {
        ParameterizedTypeReference<Employee>  parameterizedTypeReference = new ParameterizedTypeReference<Employee>(){};
        ResponseEntity<Employee> exchange =
                restTemplate.exchange("http://localhost:" + port + "/employees/" + id, HttpMethod.GET, null, parameterizedTypeReference);
        return exchange.getBody();
    }


    // curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"id": 5, "firstName": "Samwise", "middleInitial": "S", "lastName":"Nice", "dateOfBirth":"2018-04-01T07:30:00.000+00:00", "dateOfEmployment": "2018-04-01T07:30:00.000+00:00", "status": "ACTIVE"}'
}

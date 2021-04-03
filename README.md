# Employee Service
Employee Service is a JAVA rest API that provides the following methods:
* GET - Get all active employees
* GET by ID - Get an active employee by ID
  * Cannot get a non-existing employee 
* POST - Create an employee
  * Cannot create an existing employee
* PUT - Update an employee
  * Cannot update an employee to have Status:INACTIVE, must use delete request
  * Cannot update a non-existing employee  
* DELETE - Delete an employee by ID -- requires authentication
  * Cannot delete a non-existing employee

### Running Employee Service 
A java jar is included inside the bin/ directory. <br />
Running the jar with *java -jar {jar name}* will run the server on localhost:8080. 

Employee Service may also be built with maven. 

Employee Service reads from 2 JSON config files located inside the jar: employees.json and user.json. <br />
Employees.json contains all the employees, while user.json contains the credentials for authenticating requests. <br />
Employee Service stores its employees in memory, so restarting the jar will reset all changes and reload employees.json. 

### Authenticating 
Employee Service uses basic authentication for deleting employees. <br />
The credentials are in user.json, and are currently devUser:1234G. 

### Testing
Employee Service provides no front end, and therefore requires direct HTTP calls. <br />
Below are sample CURL commands for testing.
* Retrieve all employees
    * *curl -v localhost:8080/employees*
* Retrieve an employee by ID
    * *curl -v localhost:8080/employees/1*
* Create an employee
    * *curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"id": 5, "firstName": "Samwise", "middleInitial": "S", "lastName":"Gamgee", "dateOfBirth":"2018-04-01T07:30:00.000+00:00", "dateOfEmployment": "2018-04-01T07:30:00.000+00:00", "status": "ACTIVE"}'*
* Update an employee
    * *curl -X PUT localhost:8080/employees -H 'Content-type:application/json' -d '{"id": 5, "firstName": "John", "middleInitial": "S", "lastName":"Gamgee", "dateOfBirth":"2018-04-01T07:30:00.000+00:00", "dateOfEmployment": "2018-04-01T07:30:00.000+00:00", "status": "ACTIVE"}'*
* Delete an employee
    * *curl -u devUser:1234G -X DELETE localhost:8080/employees/1*

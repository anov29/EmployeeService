package com.employee.employeeservice.model;

public class Employee {

    private long id;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private int dateOfBirth;
    private int dateOfEmployment;
    private String status;

    public Employee() {
    }

    public Employee(long id, String firstName, String middleInitial, String lastName, int dateOfBirth, int dateOfEmployment, String status) {
        this.id = id;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEmployment = dateOfEmployment;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public int getDateOfEmployment() {
        return dateOfEmployment;
    }

    public String getStatus() {
        return status;
    }
}

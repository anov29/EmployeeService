package com.employee.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Date;

public class Employee {

    private long id;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private Date dateOfBirth;
    private Date dateOfEmployment;
    private State status;

    public enum State {
        ACTIVE,
        INACTIVE
    }

    public Employee() {
    }

    @JsonCreator
    public Employee(long id, String firstName, String middleInitial, String lastName, Date dateOfBirth, Date dateOfEmployment, String status) {

        this.id = id;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEmployment = dateOfEmployment;

        if (status == null) {
            this.status = State.ACTIVE; // by default employees are active
        } else {
            try {
                this.status = State.valueOf(status);
            } catch (IllegalArgumentException e) {
                this.status = State.ACTIVE; // by default employees are active
            }
        }
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public State getStatus() {
        return status;
    }
}

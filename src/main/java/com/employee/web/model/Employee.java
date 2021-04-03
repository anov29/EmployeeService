package com.employee.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Employee {

    @JsonProperty("id")
    private long id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("middleInitial")
    private String middleInitial;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("dateOfBirth")
    private Date dateOfBirth;
    @JsonProperty("dateOfEmployment")
    private Date dateOfEmployment;
    @JsonProperty("status")
    private State status;

    public enum State {
        ACTIVE,
        INACTIVE
    }

    public Employee() {
    }

    @JsonCreator
    public Employee(@JsonProperty("id") long id,@JsonProperty("firstName") String firstName,@JsonProperty("middleInitial") String middleInitial,
                    @JsonProperty("lastName") String lastName,@JsonProperty("dateOfBirth") Date dateOfBirth,@JsonProperty("dateOfEmployment") Date dateOfEmployment,
                    @JsonProperty("status") String status) {

        // date of employment cannot be before birth
        if(dateOfEmployment.before(dateOfBirth)) throw new IllegalArgumentException();

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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Employee)) {
            return false;
        }

        Employee e2 = (Employee) o;

        return this.getId() == e2.getId() && this.getStatus() == e2.getStatus() && this.getDateOfBirth().equals(e2.getDateOfBirth())
                && this.getDateOfEmployment().equals(e2.getDateOfEmployment()) && this.getFirstName().equals(e2.getFirstName())
                && this.getMiddleInitial().equals(e2.getMiddleInitial()) && this.getLastName().equals(e2.getLastName());
    }
}

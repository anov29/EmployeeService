package com.employee.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Class representing employee
 */
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
    public Employee(@JsonProperty("id") long id, @JsonProperty("firstName") String firstName, @JsonProperty("middleInitial") String middleInitial,
                    @JsonProperty("lastName") String lastName, @JsonProperty("dateOfBirth") Date dateOfBirth, @JsonProperty("dateOfEmployment") Date dateOfEmployment,
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
                this.status = State.ACTIVE;
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

    /**
     * Equals methods was not working by default, so override and manually compare each field
     * @param o Employee to compare with
     * @return if fields are the same
     */
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

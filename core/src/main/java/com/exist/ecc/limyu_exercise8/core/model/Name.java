package com.exist.ecc.limyu_exercise8.core.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Name {

    @NotNull(message = "First Name cannot be null")
    @NotEmpty(message = "First Name cannot be empty")
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @NotEmpty(message = "Last Name cannot be empty")
    private String lastName;

    private String middleName;

    private String suffix;

    private String title;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

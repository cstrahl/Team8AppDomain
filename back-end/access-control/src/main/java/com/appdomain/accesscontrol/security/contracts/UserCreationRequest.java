package com.appdomain.accesscontrol.security.contracts;

public class UserCreationRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String dateOfBirth;

    private String password;

    // Empty constructor needed for Json deserialization
    public UserCreationRequest() {
    }

    public UserCreationRequest(final String firstName,
                               final String lastName,
                               final String email,
                               final String dateOfBirth,
                               final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }
}

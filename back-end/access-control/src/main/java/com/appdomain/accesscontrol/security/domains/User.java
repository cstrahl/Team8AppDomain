package com.appdomain.accesscontrol.security.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

import static java.lang.Long.parseLong;

@Entity
@Table(schema = "PUBLIC", name = "Users")
public class User {

    @Id
    @Column(name = "Id", unique = true, nullable = false, updatable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "Username", unique = true)
    private String userName;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Date_Of_Birth")
    private Instant dateOfBirth;

    @Column(name = "Password_Hash", nullable = false)
    private String passwordHash;

    @Column(name = "Lockout_End")
    private Instant lockoutEnd;

    @Column(name = "Login_Attempts")
    private Integer loginAttempts;

    @Column(name = "Password_Create_Date", nullable = false)
    private Instant passwordCreateDate = Instant.now();

    @Column(name = "Assigned_Role")
    private String role;

    @Column(name = "Registered_By")
    private Long registeredBy;

    @Column(name = "Awaiting_Registration")
    private boolean awaitingRegistration;

    @Column(name = "Temp_Password")
    private boolean tempPassword = false;

    public User() {
    }

    public User(final String firstName,
                final String lastName,
                final String email,
                final Instant dateOfBirth,
                final String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.passwordHash = passwordHash;
        this.role = "ROLE_ANONYMOUS";
        this.awaitingRegistration = true;
        this.lockoutEnd = Instant.ofEpochMilli(parseLong("253402300799"));
        this.loginAttempts = 0;
    }

    public User(final String userName,
                final String firstName,
                final String lastName,
                final String email,
                final Instant dateOfBirth,
                final String passwordHash,
                final Instant lockoutEnd,
                final Integer loginAttempts,
                final Instant passwordCreateDate,
                final String role,
                final Long registeredBy,
                final boolean awaitingRegistration) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.passwordHash = passwordHash;
        this.lockoutEnd = lockoutEnd;
        this.loginAttempts = loginAttempts;
        this.passwordCreateDate = passwordCreateDate;
        this.role = role;
        this.registeredBy = registeredBy;
        this.awaitingRegistration = awaitingRegistration;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Instant getLockoutEnd() {
        return lockoutEnd;
    }

    public void setLockoutEnd(Instant lockoutEnd) {
        this.lockoutEnd = lockoutEnd;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Instant getPasswordCreateDate() {
        return passwordCreateDate;
    }

    public void setPasswordCreateDate(Instant passwordCreateDate) {
        this.passwordCreateDate = passwordCreateDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAwaitingRegistration() {
        return awaitingRegistration;
    }

    public void setAwaitingRegistration(boolean awaitingRegistration) {
        this.awaitingRegistration = awaitingRegistration;
    }

    public Long getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(Long registeredBy) {
        this.registeredBy = registeredBy;
    }

    public boolean isTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(boolean tempPassword) {
        this.tempPassword = tempPassword;
    }
}

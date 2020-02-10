package com.appdomain.accesscontrol.security.contracts;

import com.appdomain.accesscontrol.security.domains.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private Instant dateOfBirth;

    private Instant lockoutEnd;

    private String role;

    private boolean tempPassword;

    @JsonIgnore
    private User user;

    public CustomUserDetails(final User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.lockoutEnd = user.getLockoutEnd();
        this.role = user.getRole();
        this.tempPassword = user.isTempPassword();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return user.getLockoutEnd().isBefore(Instant.now());
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        if (user.isTempPassword()) {
            return user.getPasswordCreateDate().plus(10, ChronoUnit.MINUTES).isAfter(Instant.now());
        }
        return user.getPasswordCreateDate().plus(180, ChronoUnit.DAYS).isAfter(Instant.now());
    }

    @Override
    public boolean isEnabled() {
        return !user.isAwaitingRegistration();
    }

    public boolean isDisabled() {
        return !this.isEnabled();
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}

package com.appdomain.accesscontrol.security.contracts;

import java.util.UUID;

public class UserDetailsDto {

    private UUID uuid;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    // Empty constructor needed for Json deserialization
    public UserDetailsDto() {
    }

    public UserDetailsDto(final UUID uuid,
                          final String firstName,
                          final String lastName,
                          final String email,
                          final String role,
                          final boolean accountNonExpired,
                          final boolean accountNonLocked,
                          final boolean credentialsNonExpired,
                          final boolean enabled) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public UUID getUuid() {
        return uuid;
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

    public String getRole() {
        return role;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }
}

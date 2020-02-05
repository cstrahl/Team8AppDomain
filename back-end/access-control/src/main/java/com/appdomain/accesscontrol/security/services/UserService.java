package com.appdomain.accesscontrol.security.services;

import com.appdomain.accesscontrol.security.contracts.CustomUserDetails;
import com.appdomain.accesscontrol.security.contracts.UserRegistrationRequest;
import com.appdomain.accesscontrol.security.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public UserService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void processRegistration(final UserRegistrationRequest registrationRequest) {
        User user = this.userDetailsService.loadUserByEmail(registrationRequest.getUserEmail());
        //TODO: Create more specific error
        if (user.isAwaitingRegistration()) {
            throw new UsernameNotFoundException("No User awaiting registration found with email: " +
                    registrationRequest.getUserEmail());
        }

        final CustomUserDetails currentUser;
        try {
            currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw HttpClientErrorException.create("User Context not found", HttpStatus.UNAUTHORIZED,
                    "", null,null,null);
        }

        if (registrationRequest.isApproved()) {
            user.setUserName(getUserName(user));
            user.setRole(registrationRequest.getRole());
            user.setRegisteredBy(currentUser.getUser().getId());
            user.setLockoutEnd(Instant.now());
            user.setAwaitingRegistration(false);
            //TODO: Send email to user stating that their account has been confirmed
        } else {
            this.userDetailsService.deleteUser(user);
            //TODO: Send email to the user including registrationRequest.denialReason();
        }
    }

    private String getUserName(final User user) {
        final Calendar instance = Calendar.getInstance();
        final int month = instance.get(Calendar.MONTH) + 1;
        final int year = instance.get(Calendar.YEAR) % 100;
        return user.getFirstName().charAt(0) + user.getLastName() + month + year;
    }

    public List<CustomUserDetails> getAllUsers() {
        return this.userDetailsService.loadAllUsers().stream()
                .filter(CustomUserDetails::isEnabled)
                .collect(Collectors.toList());
    }

    public List<CustomUserDetails> getUnregisteredUsers() {
        return this.userDetailsService.loadAllUsersAwaitingRegistration();
    }
}

package com.appdomain.accesscontrol.security.services;

import com.appdomain.accesscontrol.security.contracts.CustomUserDetails;
import com.appdomain.accesscontrol.security.contracts.PasswordUpdateRequest;
import com.appdomain.accesscontrol.security.contracts.UserRegistrationRequest;
import com.appdomain.accesscontrol.security.domains.User;
import com.appdomain.accesscontrol.security.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static com.appdomain.accesscontrol.security.utils.ConstraintUtils.isValidPassword;

@Service
public class UserService {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final CustomUserDetailsService userDetailsService, final PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public void processRegistration(final UserRegistrationRequest registrationRequest) {
        User user = this.userDetailsService.loadUserByEmail(registrationRequest.getUserEmail());
        //TODO: Create more specific error
        if (user.isAwaitingRegistration()) {
            throw new UsernameNotFoundException("No User awaiting registration found with email: " +
                    registrationRequest.getUserEmail());
        }

        final CustomUserDetails currentAdmin = UserContext.getCurrentUser();
        if (!currentAdmin.getUser().getRole().equals("ROLE_ADMIN")) {
            throw HttpClientErrorException.create("Current user is not authorized for this action",
                    HttpStatus.UNAUTHORIZED, "",null,null,null);
        }
        if (registrationRequest.isApproved()) {
            user.setUserName(getUserName(user));
            user.setRole(registrationRequest.getRole());
            user.setRegisteredBy(currentAdmin.getUser().getId());
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


    public void updatePassword(final PasswordUpdateRequest updateRequest) {
        if (!isValidPassword(updateRequest.getNewPass())) {
            //TODO make special exception (see LoginService.java)
            throw HttpClientErrorException.create("Password does not meet rules", HttpStatus.BAD_REQUEST,
                    "",null,null,null);
        }

        final User user = UserContext.getCurrentUser().getUser();
        if (!user.isTempPassword()) {
            //TODO: Register users old password hash in password history table
        }

        user.setTempPassword(false);
        user.setPasswordHash(this.passwordEncoder.encode(updateRequest.getNewPass()));
        user.setPasswordCreateDate(Instant.now());
        this.userDetailsService.saveCustomUser(user);
    }
}

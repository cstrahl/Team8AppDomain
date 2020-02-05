package com.appdomain.accesscontrol.security.services;

import com.appdomain.accesscontrol.security.contracts.UserCreationRequest;
import com.appdomain.accesscontrol.security.domains.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yy/MM/dd");
//    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yy/MM/dd/HH/mm/ss");

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void processCreationRequest(final UserCreationRequest creationRequest) {
        final String passwordCandidate = creationRequest.getPassword();
        //TODO: write regex for testing password rules
        //  - Start with letter
        //  - Contain at least 1 number and 1 special character
        //  - Minimum 8 characters
        if (false) {
            //TODO: Abstract this to a CustomException where only message needs to be provided
            LOGGER.debug("Password given for user creation does not meet standards");
            throw HttpClientErrorException.create("Password does not meet rules", HttpStatus.BAD_REQUEST,
                    "",null,null,null);
        }

        //TODO: write regex for email syntax verification

        final Instant dob;
        try {
            dob = DATE_FORMAT.parse(creationRequest.getDateOfBirth()).toInstant();
        } catch (ParseException e) {
            LOGGER.debug("Provided Date of Birth is not formatted right (yy/MM/dd)", e);
            throw HttpClientErrorException.create("Date of Birth has invalid format", HttpStatus.BAD_REQUEST,
                    "",null,null,null);
        }

        //Creates the user but sets them to disabled until Admin approves them
        //Role should be assigned then, set to anonymous until then.
        this.userDetailsService.saveCustomUser(
                new User(creationRequest.getFirstName(),
                        creationRequest.getLastName(),
                        creationRequest.getEmail(),
                        dob,
                        this.passwordEncoder.encode(passwordCandidate))
        );
        LOGGER.info("User registration request created for: " + creationRequest.getEmail() +
                " | awaiting approval from Administrator");
        this.userDetailsService.getAdministrators();
        //TODO: Notify admin of user creation
    }

    public void processForgotPassword(final String email) {
        //TODO: Call email syntax check
        if (!this.userDetailsService.userExistByEmail(email)) {
            LOGGER.debug("User email provided not found on file for forgot password request");
            throw HttpClientErrorException.create("User not found", HttpStatus.BAD_REQUEST, "",
                    null,null,null);
        }
        LOGGER.debug("Sending email to user to reset password: " + email);
        //TODO: Send email
        //  Include - HMAC Secret that can be verified that user is who they say they are when making actual update.
    }
}

package com.appdomain.accesscontrol.security.services;

import com.appdomain.accesscontrol.security.contracts.UserCreationRequest;
import com.appdomain.accesscontrol.security.domains.User;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import static com.appdomain.accesscontrol.security.utils.ConstraintUtils.getPasswordRules;
import static com.appdomain.accesscontrol.security.utils.ConstraintUtils.isValidPassword;

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
        if (!isValidPassword(passwordCandidate)) {
            //TODO: Abstract this to a CustomException where only message needs to be provided
            LOGGER.debug("Password given for user creation does not meet standards");
            throw HttpClientErrorException.create("Password does not meet rules", HttpStatus.BAD_REQUEST,
                    "", HttpHeaders.EMPTY,null,null);
        }

        //TODO: write regex for email syntax verification

        final Instant dob;
        try {
            dob = DATE_FORMAT.parse(creationRequest.getDateOfBirth()).toInstant();
        } catch (ParseException e) {
            LOGGER.debug("Provided Date of Birth is not formatted right (yy/MM/dd)", e);
            throw HttpClientErrorException.create("Date of Birth has invalid format", HttpStatus.BAD_REQUEST,
                    "",HttpHeaders.EMPTY,null,null);
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
        final User user = this.userDetailsService.loadUserByEmail(email);
        final String tempPassword = this.generateTempPassword();
        user.setPasswordHash(this.passwordEncoder.encode(tempPassword));
        user.setPasswordCreateDate(Instant.now());
        user.setTempPassword(true);
        LOGGER.debug("Sending email to user to reset password: " + email);
        //TODO: Send email
    }

    private String generateTempPassword() {
        final PasswordGenerator gen = new PasswordGenerator();
        return gen.generatePassword(10, getPasswordRules());
    }

}

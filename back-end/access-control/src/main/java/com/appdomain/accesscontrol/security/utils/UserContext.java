package com.appdomain.accesscontrol.security.utils;

import com.appdomain.accesscontrol.security.contracts.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

public class UserContext {

    public static CustomUserDetails getCurrentUser() {
        try {
            return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw HttpClientErrorException.create("User Context not found", HttpStatus.UNAUTHORIZED,
                    "", null,null,null);
        }
    }
}

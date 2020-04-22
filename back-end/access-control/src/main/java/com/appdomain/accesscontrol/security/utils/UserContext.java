package com.appdomain.accesscontrol.security.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

public class UserContext {

    public static String getCurrentUserName() {
        try {
            return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw HttpClientErrorException.create("User Context not found", HttpStatus.UNAUTHORIZED,
                    "", HttpHeaders.EMPTY,null,null);
        }
    }
}

package com.appdomain.accesscontrol.configurations;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String testToken() {
        return "This User is an Admin";
    }

}

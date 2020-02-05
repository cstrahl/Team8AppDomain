package com.appdomain.accesscontrol.security.controllers;

import com.appdomain.accesscontrol.security.contracts.CustomUserDetails;
import com.appdomain.accesscontrol.security.contracts.UserRegistrationRequest;
import com.appdomain.accesscontrol.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole(T(com.appdomain.accesscontrol.security.services.enums.Roles).ADMIN.name())")
    @GetMapping("/list")
    public List<CustomUserDetails> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PreAuthorize("hasRole(T(com.appdomain.accesscontrol.security.services.enums.Roles).ADMIN.name())")
    @GetMapping("/unregistered")
    public List<CustomUserDetails> getUnregisteredUsers() {
        return this.userService.getUnregisteredUsers();
    }

    @PreAuthorize("hasRole(T(com.appdomain.accesscontrol.security.services.enums.Roles).ADMIN.name())")
    @PutMapping("/process-registration")
    public void processRegistration(@RequestBody UserRegistrationRequest registrationRequest) {
        this.userService.processRegistration(registrationRequest);
    }
}
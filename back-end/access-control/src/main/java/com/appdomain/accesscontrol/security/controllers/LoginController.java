package com.appdomain.accesscontrol.security.controllers;

import com.appdomain.accesscontrol.security.contracts.UserCreationRequest;
import com.appdomain.accesscontrol.security.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@PreAuthorize("permitAll()")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/user-creation-request")
    public void requestUserCreation(@RequestBody final UserCreationRequest creationRequest) {
        this.loginService.processCreationRequest(creationRequest);
    }

    @GetMapping("/forgot-password")
    public void forgotPassword(@RequestBody final String email) {
        this.loginService.processForgotPassword(email);
    }
}

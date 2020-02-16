package com.appdomain.accesscontrol.accounting.controllers;

import com.appdomain.accesscontrol.accounting.contracts.AccountDto;
import com.appdomain.accesscontrol.accounting.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Map<Long, AccountDto> getAccounts() {
        return this.accountService.getAllAccounts();
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("isAuthenticated()")
    public AccountDto getAccount(@PathVariable final long accountId) {
        return this.accountService.getAccount(accountId);
    }

    @PostMapping
    @PreAuthorize("hasRole(ADMIN)")
    public void createAccount(@RequestBody final AccountDto accountDto) {
        this.accountService.createAccount(accountDto);
    }

    @PutMapping
    @PreAuthorize("hasRole(ADMIN)")
    public void updateAccount(@RequestBody final AccountDto accountDto) {
        this.accountService.updateAccount(accountDto);
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("hasRole(ADMIN)")
    public void disableAccount(@PathVariable final long accountId) {
        this.accountService.disableAccount(accountId);
    }
}

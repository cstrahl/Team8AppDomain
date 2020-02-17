package com.appdomain.accesscontrol.accounting.services;

import com.appdomain.accesscontrol.accounting.contracts.AccountDto;
import com.appdomain.accesscontrol.accounting.domains.Account;
import com.appdomain.accesscontrol.accounting.repositories.AccountRepository;
import com.appdomain.accesscontrol.security.contracts.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(final AccountDto accountDto) {
        final String currentUser;
        try {
            currentUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw HttpClientErrorException.create("User Context not found", HttpStatus.UNAUTHORIZED,
                    "", null,null,null);
        }
        this.accountRepository.save(
                new Account(
                        accountDto.getName(),
                        true,
                        accountDto.getDescription(),
                        accountDto.getNormalSide().name(),
                        accountDto.getCategory(),
                        accountDto.getSubcategory(),
                        accountDto.getInitialBalance(),
                        accountDto.getDebit(),
                        accountDto.getCredit(),
                        accountDto.getBalance(),
                        Instant.now(),
                        currentUser,
                        accountDto.getOrder(),
                        accountDto.getStatementName(),
                        accountDto.getComment()));
    }

    public Map<Long, AccountDto> getAllAccounts() {
        return this.accountRepository.findAllByOrderBySortOrderAsc().stream()
                .filter(Account::isEnabled)
                .collect(Collectors.toMap(Account::getId, AccountDto::new));
    }

    public AccountDto getAccount(final long accountId) {
        return new AccountDto(getAccountOrThrowException(accountId));
    }

    public void updateAccount(final AccountDto accountDto) {
        final Account account = getAccountOrThrowException(accountDto.getAccountNumber());
        account.setName(accountDto.getName());
        account.setDescription(accountDto.getDescription());
        account.setSide(accountDto.getNormalSide().name());
        account.setCategory(accountDto.getCategory());
        account.setSubcategory(accountDto.getSubcategory());
        account.setDebit(accountDto.getDebit());
        account.setCredit(accountDto.getCredit());
        account.setBalance(accountDto.getBalance());
        account.setSortOrder(accountDto.getOrder());
        account.setStatement(accountDto.getStatementName());
        account.setComments(accountDto.getComment());
        this.accountRepository.save(account);
    }

    private Account getAccountOrThrowException(final long accountNumber) {
        Account account = this.accountRepository.findById(accountNumber)
                .orElseThrow(() -> HttpClientErrorException.create("No active Account found with id",
                        HttpStatus.BAD_REQUEST, "", null, null, Charset.defaultCharset()));
        if (!account.isEnabled()) {
            throw HttpClientErrorException.create("No active Account found with id",
                    HttpStatus.BAD_REQUEST, "", null, null, Charset.defaultCharset());
        }
        return account;
    }

    public void disableAccount(final long accountId) {
        final Account account = this.getAccountOrThrowException(accountId);
        if (account.getBalance() != 0) {
            throw HttpClientErrorException.create("Account does not have zero balance", HttpStatus.BAD_REQUEST,
                    "", null, null, Charset.defaultCharset());
        }
        account.setEnabled(false);
        this.accountRepository.save(account);
    }
}

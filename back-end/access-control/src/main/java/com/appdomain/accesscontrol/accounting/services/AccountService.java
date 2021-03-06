package com.appdomain.accesscontrol.accounting.services;

import com.appdomain.accesscontrol.accounting.contracts.AccountDto;
import com.appdomain.accesscontrol.accounting.domains.Account;
import com.appdomain.accesscontrol.accounting.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                    "", HttpHeaders.EMPTY, null, null);
        }
        this.accountRepository.save(
                new Account(
                        accountDto.getName(),
                        true,
                        accountDto.getDescription(),
                        accountDto.getNormalSide().name(),
                        accountDto.getCategory().name(),
                        accountDto.getSubcategory().name(),
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

    public Map<Long, AccountDto> getAllAccountsDtoMap() {
        return this.accountRepository.findAllByOrderBySortOrderAsc().stream()
                .filter(Account::isEnabled)
                .collect(Collectors.toMap(Account::getId, AccountDto::new));
    }

    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    public AccountDto getAccount(final long accountId) {
        return new AccountDto(getAccountOrThrowException(accountId));
    }

    public Account getAccountById(final long accountId) {
        return this.getAccountOrThrowException(accountId);
    }

    public void updateAccount(final AccountDto accountDto) {
        final Account account = getAccountOrThrowException(accountDto.getAccountNumber());
        account.setName(accountDto.getName());
        account.setDescription(accountDto.getDescription());
        account.setSide(accountDto.getNormalSide().name());
        account.setCategory(accountDto.getCategory().name());
        account.setInitialBalance(accountDto.getInitialBalance());
        account.setSubcategory(accountDto.getSubcategory().name());
        account.setDebit(accountDto.getDebit());
        account.setCredit(accountDto.getCredit());
        account.setBalance(accountDto.getBalance());
        account.setSortOrder(accountDto.getOrder());
        account.setStatement(accountDto.getStatementName());
        account.setComments(accountDto.getComment());
        this.accountRepository.save(account);
    }

    public boolean allAccountsExist(final Set<Long> ids) {
        return !ids.isEmpty() && this.accountRepository.countDistinctByIdIn(ids).equals((long) ids.size());
    }

    private Account getAccountOrThrowException(final long accountNumber) {
        Account account = this.accountRepository.findById(accountNumber)
                .orElseThrow(() -> HttpClientErrorException.create("No active Account found with id",
                        HttpStatus.BAD_REQUEST, "", HttpHeaders.EMPTY, null, Charset.defaultCharset()));
        if (!account.isEnabled()) {
            throw HttpClientErrorException.create("No active Account found with id",
                    HttpStatus.BAD_REQUEST, "", HttpHeaders.EMPTY, null, Charset.defaultCharset());
        }
        return account;
    }

    public void disableAccount(final long accountId) {
        final Account account = this.getAccountOrThrowException(accountId);
        if (account.getBalance() != 0) {
            throw HttpClientErrorException.create("Account does not have zero balance", HttpStatus.BAD_REQUEST,
                    "", HttpHeaders.EMPTY, null, Charset.defaultCharset());
        }
        account.setEnabled(false);
        this.accountRepository.save(account);
    }

    public void saveAll(final Collection<Account> accounts) {
        this.accountRepository.saveAll(accounts);
    }
}

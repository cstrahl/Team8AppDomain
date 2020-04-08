package com.appdomain.accesscontrol.accounting.contracts;

import com.appdomain.accesscontrol.accounting.domains.Account;
import com.appdomain.accesscontrol.accounting.utils.AccountCategory;
import com.appdomain.accesscontrol.accounting.utils.AccountSubCategory;
import com.appdomain.accesscontrol.accounting.utils.TransactionType;

import java.time.Instant;

public class AccountDto {

    private String name;
    private long accountNumber;
    private String description;
    private TransactionType normalSide;
    private AccountCategory category;
    private AccountSubCategory subcategory;
    private double initialBalance;
    private double debit;
    private double credit;
    private double balance;
    private Instant accountAdded;
    private String userId;
    private int order;
    private String statementName;
    private String comment;

    public AccountDto() {
    }

    public AccountDto(final Account account) {
        this.name = account.getName();
        this.accountNumber = account.getId();
        this.description = account.getDescription();
        this.normalSide = TransactionType.valueOf(account.getSide());
        this.category = AccountCategory.valueOf(account.getCategory());
        this.subcategory = AccountSubCategory.valueOf(account.getSubcategory());
        this.initialBalance = account.getInitialBalance();
        this.debit = account.getDebit();
        this.credit = account.getCredit();
        this.balance = account.getBalance();
        this.accountAdded = account.getAccountAdded();
        this.userId = account.getCreatedBy();
        this.order = account.getSortOrder();
        this.statementName = account.getStatement();
        this.comment = account.getComments();
    }

    public String getName() {
        return name;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getNormalSide() {
        return normalSide;
    }

    public AccountCategory getCategory() {
        return category;
    }

    public AccountSubCategory getSubcategory() {
        return subcategory;
    }

    public double getBalance() {
        return balance;
    }

    public double getDebit() {
        return debit;
    }

    public double getCredit() {
        return credit;
    }

    public Instant getAccountAdded() {
        return accountAdded;
    }

    public String getUserId() {
        return userId;
    }

    public int getOrder() {
        return order;
    }

    public String getStatementName() {
        return statementName;
    }

    public String getComment() {
        return comment;
    }

    public double getInitialBalance() {
        return initialBalance;
    }
}

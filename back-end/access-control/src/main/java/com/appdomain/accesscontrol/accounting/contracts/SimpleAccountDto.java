package com.appdomain.accesscontrol.accounting.contracts;

import com.appdomain.accesscontrol.accounting.utils.AccountSubCategory;
import com.appdomain.accesscontrol.accounting.utils.TransactionType;

public class SimpleAccountDto {

    private String accountName;
    private TransactionType normalSide;
    private AccountSubCategory accountSubCategory;
    private double debit;
    private double credit;

    public SimpleAccountDto() {
    }

    public SimpleAccountDto(final String accountName,
                            final TransactionType normalSide,
                            final AccountSubCategory accountSubCategory,
                            final double debit,
                            final double credit) {
        this.accountName = accountName;
        this.normalSide = normalSide;
        this.accountSubCategory = accountSubCategory;
        this.debit = debit;
        this.credit = credit;
    }

    public String getAccountName() {
        return accountName;
    }

    public TransactionType getNormalSide() {
        return normalSide;
    }

    public AccountSubCategory getAccountSubCategory() {
        return accountSubCategory;
    }

    public double getDebit() {
        return debit;
    }

    public double getCredit() {
        return credit;
    }
}

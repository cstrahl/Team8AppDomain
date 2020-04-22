package com.appdomain.accesscontrol.accounting.contracts;

import java.util.List;

public class TrialBalanceDto {

    private List<SimpleAccountDto> accountEntries;
    private double totalDebit;
    private double totalCredit;

    public TrialBalanceDto() {
    }

    public TrialBalanceDto(final List<SimpleAccountDto> accountEntries,
                           final double totalDebit, final double totalCredit) {
        this.accountEntries = accountEntries;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
    }

    public List<SimpleAccountDto> getAccountEntries() {
        return accountEntries;
    }

    public double getTotalDebit() {
        return totalDebit;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalDebit(double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }
}

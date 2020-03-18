package com.appdomain.accesscontrol.accounting.contracts;

import com.appdomain.accesscontrol.accounting.domains.LedgerEntry;

public class LedgerEntryDto {

    private long id;
    private long accountId;
    private long journalEntryId;
    private double credit;
    private double debit;
    private String description;
    private boolean pending;

    public LedgerEntryDto() {
    }

    public LedgerEntryDto(final LedgerEntry ledgerEntry) {
        this.id = ledgerEntry.getId();
        this.accountId = ledgerEntry.getAccountId();
        this.journalEntryId = ledgerEntry.getJournalEntryId();
        this.credit = ledgerEntry.getCredit();
        this.debit = ledgerEntry.getDebit();
        this.description = ledgerEntry.getDescription();
        this.pending = ledgerEntry.isPending();
    }

    public long getId() {
        return id;
    }

    public long getAccountId() {
        return accountId;
    }

    public long getJournalEntryId() {
        return journalEntryId;
    }

    public double getCredit() {
        return credit;
    }

    public double getDebit() {
        return debit;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPending() {
        return pending;
    }
}

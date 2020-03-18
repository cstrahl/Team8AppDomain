package com.appdomain.accesscontrol.accounting.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(schema = "PUBLIC", catalog = "LedgerEntries")
public class LedgerEntry {

    @Id
    @Column(name = "Id", unique = true, nullable = false, updatable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "Account_Id", nullable = false)
    private Long accountId;

    @Column(name = "Journal_Entry_Id", nullable = false)
    private Long journalEntryId;

    @Column(name = "Create_Date", nullable = false, updatable = false)
    private Instant createDate;

    @Column(name = "Credit")
    private Double credit;

    @Column(name = "Debit")
    private Double debit;

    @Column(name = "Description")
    private String description;

    @Column(name= "Pending", nullable = false)
    private Boolean pending;

    public LedgerEntry() {
    }

    public LedgerEntry(final Long accountId,
                       final Long journalEntryId,
                       final Double credit,
                       final Double debit,
                       final String description) {
        this.accountId = accountId;
        this.journalEntryId = journalEntryId;
        this.pending = true;
        this.createDate = Instant.now();
        this.credit = credit;
        this.debit = debit;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getJournalEntryId() {
        return journalEntryId;
    }

    public void setJournalEntryId(Long journalEntryId) {
        this.journalEntryId = journalEntryId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }
}

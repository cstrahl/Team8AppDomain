package com.appdomain.accesscontrol.accounting.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(schema = "PUBLIC", name = "Accounts")
public class Account {

    @Id
    @Column(name = "Id", unique = true, nullable = false, updatable = false)
    @GeneratedValue
    private long id;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Enabled")
    private boolean enabled;

    @Column(name = "Description")
    private String description;

    @Column(name = "Side", nullable = false)
    private String side;

    @Column(name = "Category")
    private String category;

    @Column(name = "Subcategory")
    private String subcategory;

    @Column(name = "Initial_Balance", nullable = false)
    private double initialBalance;

    @Column(name = "Debit", nullable = false)
    private double debit;

    @Column(name = "Credit", nullable = false)
    private double credit;

    @Column(name = "Balance", nullable = false)
    private double balance;

    @Column(name = "Account_Added", nullable = false, updatable = false)
    private Instant accountAdded;

    @Column(name = "Created_By", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "Sort_Order", nullable = false)
    private int sortOrder;

    @Column(name = "Statement")
    private String statement;

    @Column(name = "Comments")
    private String comments;

    public Account() {
    }

    public Account(final String name,
                   final boolean enabled,
                   final String description,
                   final String side,
                   final String category,
                   final String subcategory,
                   final double initialBalance,
                   final double debit,
                   final double credit,
                   final double balance,
                   final Instant accountAdded,
                   final String createdBy,
                   final int sortOrder,
                   final String statement,
                   final String comments) {
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.side = side;
        this.category = category;
        this.subcategory = subcategory;
        this.initialBalance = initialBalance;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.accountAdded = accountAdded;
        this.createdBy = createdBy;
        this.sortOrder = sortOrder;
        this.statement = statement;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSide() {
        return side;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public double getDebit() {
        return debit;
    }

    public double getCredit() {
        return credit;
    }

    public double getBalance() {
        return balance;
    }

    public Instant getAccountAdded() {
        return accountAdded;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getStatement() {
        return statement;
    }

    public String getComments() {
        return comments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

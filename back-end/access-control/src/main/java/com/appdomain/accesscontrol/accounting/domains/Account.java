package com.appdomain.accesscontrol.accounting.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(schema = "PUBLIC",name = "Accounts")
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

    @Column(name = "InitialBalance", nullable = false)
    private double initialBalance;

    @Column(name = "Debit", nullable = false)
    private double debit;

    @Column(name = "Credit", nullable = false)
    private double credit;

    @Column(name = "Balance", nullable = false)
    private double balance;

    @Column(name = "AccountAdded", nullable = false, updatable = false)
    private Instant accountAdded;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private long createdBy;

    @Column(name = "Order")
    private int order;

    @Column(name = "Statement")
    private String statement;

    @Column(name = "Comment")
    private String comment;

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
                   final long createdBy,
                   final int order,
                   final String statement,
                   final String comment) {
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
        this.order = order;
        this.statement = statement;
        this.comment = comment;
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

    public long getCreatedBy() {
        return createdBy;
    }

    public int getOrder() {
        return order;
    }

    public String getStatement() {
        return statement;
    }

    public String getComment() {
        return comment;
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

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

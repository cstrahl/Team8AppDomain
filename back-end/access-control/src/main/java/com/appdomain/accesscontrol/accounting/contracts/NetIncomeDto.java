package com.appdomain.accesscontrol.accounting.contracts;

import java.util.List;

public class NetIncomeDto {

    private List<SimpleAccountDto> revenues;
    private Double totalRevenue;
    private List<SimpleAccountDto> expenses;
    private Double totalExpenses;
    private Double grossNetIncome;

    public NetIncomeDto() {
    }

    public NetIncomeDto(final List<SimpleAccountDto> revenues,
                        final Double totalRevenue,
                        final List<SimpleAccountDto> expenses,
                        final Double totalExpenses,
                        final Double grossNetIncome) {
        this.revenues = revenues;
        this.totalRevenue = totalRevenue;
        this.expenses = expenses;
        this.totalExpenses = totalExpenses;
        this.grossNetIncome = grossNetIncome;
    }

    public List<SimpleAccountDto> getRevenues() {
        return revenues;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public List<SimpleAccountDto> getExpenses() {
        return expenses;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public Double getGrossNetIncome() {
        return grossNetIncome;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void setGrossNetIncome(Double grossNetIncome) {
        this.grossNetIncome = grossNetIncome;
    }
}

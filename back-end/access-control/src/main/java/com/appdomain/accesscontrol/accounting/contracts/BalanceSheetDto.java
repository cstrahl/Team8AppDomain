package com.appdomain.accesscontrol.accounting.contracts;

import java.util.List;

public class BalanceSheetDto {

    private List<SimpleAccountDto> assets;
    private Double totalAssets;
    private List<SimpleAccountDto> liabilities;
    private Double totalLiabilities;
    private List<SimpleAccountDto> equity;
    private Double totalEquity;
    private Double totalLiabilitiesAndEquity;

    public BalanceSheetDto() {
    }

    public BalanceSheetDto(final List<SimpleAccountDto> assets,
                           final double totalAssets,
                           final List<SimpleAccountDto> liabilities,
                           final double totalLiabilities,
                           final List<SimpleAccountDto> equity,
                           final double totalEquity,
                           final double totalLiabilitiesAndEquity) {
        this.assets = assets;
        this.totalAssets = totalAssets;
        this.liabilities = liabilities;
        this.totalLiabilities = totalLiabilities;
        this.equity = equity;
        this.totalEquity = totalEquity;
        this.totalLiabilitiesAndEquity = totalLiabilitiesAndEquity;
    }

    public List<SimpleAccountDto> getAssets() {
        return assets;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public List<SimpleAccountDto> getLiabilities() {
        return liabilities;
    }

    public Double getTotalLiabilities() {
        return totalLiabilities;
    }

    public List<SimpleAccountDto> getEquity() {
        return equity;
    }

    public Double getTotalEquity() {
        return totalEquity;
    }

    public Double getTotalLiabilitiesAndEquity() {
        return totalLiabilitiesAndEquity;
    }

    public void setTotalLiabilitiesAndEquity(Double totalLiabilitiesAndEquity) {
        this.totalLiabilitiesAndEquity = totalLiabilitiesAndEquity;
    }
}

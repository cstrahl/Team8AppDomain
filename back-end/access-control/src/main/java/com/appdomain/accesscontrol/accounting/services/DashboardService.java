package com.appdomain.accesscontrol.accounting.services;

import com.appdomain.accesscontrol.accounting.contracts.BalanceSheetDto;
import com.appdomain.accesscontrol.accounting.contracts.NetIncomeDto;
import com.appdomain.accesscontrol.accounting.contracts.RatioDto;
import com.appdomain.accesscontrol.accounting.utils.AlertLevel;
import org.springframework.stereotype.Service;

import static com.appdomain.accesscontrol.accounting.utils.AccountSubCategory.DEBT;
import static com.appdomain.accesscontrol.accounting.utils.AccountSubCategory.INVENTORY;

@Service
public class DashboardService {

    private final ReportService reportService;

    public DashboardService(final ReportService reportService) {
        this.reportService = reportService;
    }

    public RatioDto calculateProfitMargin() {
        final NetIncomeDto netIncome = this.reportService.getNetIncome();
        final double profitMargin = toPercent(netIncome.getGrossNetIncome() / netIncome.getTotalRevenue());
        if (profitMargin >= 5) {
            if (profitMargin >= 15) {
                return new RatioDto(profitMargin, AlertLevel.GREEN);
            }
            return new RatioDto(profitMargin, AlertLevel.YELLOW);
        }
        return new RatioDto(profitMargin, AlertLevel.RED);
    }

    public RatioDto calculateReturnOnAssets() {
        final NetIncomeDto netIncome = this.reportService.getNetIncome();
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double roi = toPercent(netIncome.getGrossNetIncome() / balanceSheet.getTotalAssets());
        if (roi > 0) {
            if (roi >= 10) {
                return new RatioDto(roi, AlertLevel.GREEN);
            }
            return new RatioDto(roi, AlertLevel.YELLOW);
        }
        return new RatioDto(roi, AlertLevel.RED);
    }

    public RatioDto calculateReturnOnEquity() {
        final NetIncomeDto netIncome = this.reportService.getNetIncome();
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double roe = toPercent(netIncome.getGrossNetIncome() / balanceSheet.getTotalEquity());
        if (roe > 0) {
            if (roe >= 15) {
                return new RatioDto(roe, AlertLevel.GREEN);
            }
            return new RatioDto(roe, AlertLevel.YELLOW);
        }
        return new RatioDto(roe, AlertLevel.RED);
    }

    public RatioDto calculateCurrentRatio() {
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double currentRatio = toPercent(balanceSheet.getTotalAssets() / balanceSheet.getTotalLiabilities());
        if (currentRatio > 100) {
            if (currentRatio >= 200) {
                return new RatioDto(currentRatio, AlertLevel.GREEN);
            }
            return new RatioDto(currentRatio, AlertLevel.YELLOW);
        }
        return new RatioDto(currentRatio, AlertLevel.RED);
    }

    public RatioDto calculateQuickRatio() {
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double totalNonLiquidAssets = this.getTotalNonLiquidAssets(balanceSheet);
        final double currentRatio = toPercent(balanceSheet.getTotalAssets() - totalNonLiquidAssets
                / balanceSheet.getTotalLiabilities());
        if (currentRatio > 100) {
            if (currentRatio >= 120) {
                return new RatioDto(currentRatio, AlertLevel.GREEN);
            }
            return new RatioDto(currentRatio, AlertLevel.YELLOW);
        }
        return new RatioDto(currentRatio, AlertLevel.RED);
    }

    public RatioDto calculateInventoryRatio() {
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double totalNonLiquidAssets = this.getTotalNonLiquidAssets(balanceSheet);
        final double currentRatio = toPercent(totalNonLiquidAssets
                / balanceSheet.getTotalAssets() - balanceSheet.getTotalLiabilities());
        if (currentRatio < 100) {
            if (currentRatio <= 75) {
                return new RatioDto(currentRatio, AlertLevel.GREEN);
            }
            return new RatioDto(currentRatio, AlertLevel.YELLOW);
        }
        return new RatioDto(currentRatio, AlertLevel.RED);
    }

    public RatioDto calculateDebtAssetRatio() {
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double ratio = toPercent(this.getTotalDebt(balanceSheet) / balanceSheet.getTotalAssets());
        if (ratio < 60) {
            if (ratio <= 40) {
                return new RatioDto(ratio, AlertLevel.GREEN);
            }
            return new RatioDto(ratio, AlertLevel.YELLOW);
        }
        return new RatioDto(ratio, AlertLevel.RED);
    }

    public RatioDto calculateDebtEquityRatio() {
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double ratio = toPercent(this.getTotalDebt(balanceSheet) / balanceSheet.getTotalEquity());
        if (ratio > 100) {
            if (ratio >= 150) {
                return new RatioDto(ratio, AlertLevel.GREEN);
            }
            return new RatioDto(ratio, AlertLevel.YELLOW);
        }
        return new RatioDto(ratio, AlertLevel.RED);
    }

    public RatioDto calculateAssetTurnover() {
        final NetIncomeDto netIncome = this.reportService.getNetIncome();
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double ratio = toPercent(netIncome.getGrossNetIncome() / balanceSheet.getTotalAssets());
        if (ratio > 100) {
            if (ratio >= 150) {
                return new RatioDto(ratio, AlertLevel.GREEN);
            }
            return new RatioDto(ratio, AlertLevel.YELLOW);
        }
        return new RatioDto(ratio, AlertLevel.RED);
    }

    public RatioDto calculateInventoryTurnover() {
        final NetIncomeDto netIncome = this.reportService.getNetIncome();
        final BalanceSheetDto balanceSheet = this.reportService.getBalanceSheet();
        final double ratio = toPercent(netIncome.getGrossNetIncome()
                / this.getTotalNonLiquidAssets(balanceSheet));
        if (ratio > 400) {
            if (ratio >= 600) {
                return new RatioDto(ratio, AlertLevel.GREEN);
            }
            return new RatioDto(ratio, AlertLevel.YELLOW);
        }
        return new RatioDto(ratio, AlertLevel.RED);
    }

    private double getTotalDebt(final BalanceSheetDto balanceSheet) {
        return balanceSheet.getLiabilities().stream()
                .filter(liabilities -> DEBT.equals(liabilities.getAccountSubCategory()))
                .mapToDouble(liabilities -> liabilities.getDebit() - liabilities.getCredit())
                .sum();
    }

    private double getTotalNonLiquidAssets(final BalanceSheetDto balanceSheet) {
        return balanceSheet.getAssets().stream()
                .filter(assets -> INVENTORY.equals(assets.getAccountSubCategory()))
                .mapToDouble(assets -> assets.getDebit() - assets.getCredit())
                .sum();
    }

    private static double toPercent(final double value) {
        return Math.rint(value * 10000) / 100;
    }
}

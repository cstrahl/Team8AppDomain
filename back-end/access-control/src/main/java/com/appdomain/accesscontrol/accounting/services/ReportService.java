package com.appdomain.accesscontrol.accounting.services;

import com.appdomain.accesscontrol.accounting.contracts.BalanceSheetDto;
import com.appdomain.accesscontrol.accounting.contracts.NetIncomeDto;
import com.appdomain.accesscontrol.accounting.contracts.SimpleAccountDto;
import com.appdomain.accesscontrol.accounting.contracts.TrialBalanceDto;
import com.appdomain.accesscontrol.accounting.domains.Account;
import com.appdomain.accesscontrol.accounting.utils.AccountCategory;
import com.appdomain.accesscontrol.accounting.utils.AccountSubCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.appdomain.accesscontrol.accounting.utils.TransactionType.CREDIT;
import static com.appdomain.accesscontrol.accounting.utils.TransactionType.DEBIT;

@Service
public class ReportService {

    private final AccountService accountService;

    public ReportService(final AccountService accountService) {
        this.accountService = accountService;
    }

    public TrialBalanceDto getTrialBalance() {
        final TrialBalanceDto trialBalanceDto = new TrialBalanceDto(new ArrayList<>(), 0.0, 0.0);
        final List<Account> accounts = this.accountService.getAllAccounts();
        accounts.forEach(account -> this.incrementAccount(account, trialBalanceDto));
        return trialBalanceDto;
    }

    public BalanceSheetDto getBalanceSheet() {
        final BalanceSheetDto balanceSheetDto = new BalanceSheetDto(new ArrayList<>(), 0.0,
                new ArrayList<>(), 0.0,
                new ArrayList<>(), 0.0, 0.0);
        final List<Account> accounts = this.accountService.getAllAccounts();
        for (Account account : accounts) {
            switch (AccountCategory.valueOf(account.getCategory())) {
                case ASSET:
                    if (DEBIT.name().equals(account.getSide())) {
                        balanceSheetDto.getAssets().add(new SimpleAccountDto(account.getName(), DEBIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), account.getBalance(), 0.0));
                    } else {
                        balanceSheetDto.getAssets().add(new SimpleAccountDto(account.getName(), CREDIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), 0.0, account.getBalance()));
                    }
                    balanceSheetDto.setTotalAssets(balanceSheetDto.getTotalAssets() + account.getBalance());
                    break;
                case LIABILITY:
                    if (DEBIT.name().equals(account.getSide())) {
                        balanceSheetDto.getLiabilities().add(new SimpleAccountDto(account.getName(), DEBIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), account.getBalance(), 0.0));
                    } else {
                        balanceSheetDto.getLiabilities().add(new SimpleAccountDto(account.getName(), CREDIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), 0.0, account.getBalance()));
                    }
                    balanceSheetDto.setTotalLiabilities(balanceSheetDto.getTotalLiabilities() + account.getBalance());
                    break;
                case EQUITY:
                    if (DEBIT.name().equals(account.getSide())) {
                        balanceSheetDto.getEquity().add(new SimpleAccountDto(account.getName(), DEBIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), account.getBalance(), 0.0));
                    } else {
                        balanceSheetDto.getEquity().add(new SimpleAccountDto(account.getName(), CREDIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), 0.0, account.getBalance()));
                    }
                    balanceSheetDto.setTotalEquity(balanceSheetDto.getTotalEquity() + account.getBalance());
                    break;
            }
        }
        balanceSheetDto.setTotalLiabilitiesAndEquity(balanceSheetDto.getTotalEquity()
                + balanceSheetDto.getTotalLiabilities());
        return balanceSheetDto;
    }

    public NetIncomeDto getNetIncome() {
        final NetIncomeDto netIncomeDto = new NetIncomeDto(new ArrayList<>(), 0.0,
                new ArrayList<>(), 0.0, 0.0);
        final List<Account> accounts = this.accountService.getAllAccounts();
        for (Account account : accounts) {
            switch (AccountSubCategory.valueOf(account.getSubcategory())) {
                case REVENUE:
                    if (DEBIT.name().equals(account.getSide())) {
                        netIncomeDto.getRevenues().add(new SimpleAccountDto(account.getName(), DEBIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), account.getBalance(), 0.0));
                    } else {
                        netIncomeDto.getRevenues().add(new SimpleAccountDto(account.getName(), CREDIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), 0.0, account.getBalance()));
                    }
                    netIncomeDto.setTotalRevenue(netIncomeDto.getTotalRevenue() + account.getBalance());
                    break;
                case EXPENSE:
                    if (DEBIT.name().equals(account.getSide())) {
                        netIncomeDto.getExpenses().add(new SimpleAccountDto(account.getName(), DEBIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), account.getBalance(), 0.0));
                    } else {
                        netIncomeDto.getExpenses().add(new SimpleAccountDto(account.getName(), CREDIT,
                                AccountSubCategory.valueOf(account.getSubcategory()), 0.0, account.getBalance()));
                    }
                    netIncomeDto.setTotalExpenses(netIncomeDto.getTotalExpenses() + account.getBalance());
                    break;
            }
        }
        netIncomeDto.setGrossNetIncome(netIncomeDto.getTotalRevenue() - netIncomeDto.getTotalExpenses());
        return netIncomeDto;
    }

    private void incrementAccount(final Account account, final TrialBalanceDto trialBalanceDto) {
        if (DEBIT.name().equals(account.getSide())) {
            trialBalanceDto.getAccountEntries().add(new SimpleAccountDto(account.getName(), DEBIT,
                    AccountSubCategory.valueOf(account.getSubcategory()), account.getBalance(), 0.0));
            trialBalanceDto.setTotalDebit(trialBalanceDto.getTotalDebit() + account.getBalance());
        } else {
            trialBalanceDto.getAccountEntries().add(new SimpleAccountDto(account.getName(), CREDIT,
                    AccountSubCategory.valueOf(account.getSubcategory()), 0.0, account.getBalance()));
            trialBalanceDto.setTotalCredit(trialBalanceDto.getTotalCredit() + account.getBalance());
        }
    }
}

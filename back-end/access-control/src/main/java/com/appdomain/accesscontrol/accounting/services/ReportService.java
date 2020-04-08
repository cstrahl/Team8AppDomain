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
        accounts.forEach(account -> this.incrementAccount(account, trialBalanceDto.getAccountEntries(),
                trialBalanceDto.getTotalDebit(), trialBalanceDto.getTotalCredit()));
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
                    this.incrementAccount(account, balanceSheetDto.getAssets(),
                            balanceSheetDto.getTotalAssets(), balanceSheetDto.getTotalAssets());
                    break;
                case LIABILITY:
                    this.incrementAccount(account, balanceSheetDto.getLiabilities(),
                            balanceSheetDto.getTotalLiabilities(), balanceSheetDto.getTotalLiabilities());
                    break;
                case EQUITY:
                    this.incrementAccount(account, balanceSheetDto.getEquity(),
                            balanceSheetDto.getTotalEquity(), balanceSheetDto.getTotalEquity());
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
                    this.incrementAccount(account, netIncomeDto.getRevenues(),
                            netIncomeDto.getTotalRevenue(), netIncomeDto.getTotalRevenue());
                    break;
                case EXPENSE:
                    this.incrementAccount(account, netIncomeDto.getExpenses(),
                            netIncomeDto.getTotalExpenses(), netIncomeDto.getTotalExpenses());
                    break;
            }
        }
        netIncomeDto.setGrossNetIncome(netIncomeDto.getTotalRevenue() - netIncomeDto.getTotalExpenses());
        return netIncomeDto;
    }

    private void incrementAccount(final Account account, final List<SimpleAccountDto> accountList,
                                  Double debit, Double credit) {
        if (DEBIT.name().equals(account.getSide())) {
            accountList.add(new SimpleAccountDto(account.getName(), DEBIT, account.getBalance(), 0.0));
            debit += account.getBalance();
        } else {
            accountList.add(new SimpleAccountDto(account.getName(), CREDIT, 0.0, account.getBalance()));
            credit += account.getBalance();
        }
    }
}

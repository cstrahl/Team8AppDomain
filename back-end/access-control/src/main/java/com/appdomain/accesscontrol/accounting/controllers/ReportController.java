package com.appdomain.accesscontrol.accounting.controllers;

import com.appdomain.accesscontrol.accounting.contracts.BalanceSheetDto;
import com.appdomain.accesscontrol.accounting.contracts.NetIncomeDto;
import com.appdomain.accesscontrol.accounting.contracts.TrialBalanceDto;
import com.appdomain.accesscontrol.accounting.services.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {


    private ReportService reportService;

    public ReportController(final ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/trial-balance")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public TrialBalanceDto getTrialBalance() {
        return this.reportService.getTrialBalance();
    }

    @GetMapping("/balance-sheet")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public BalanceSheetDto getBalanceSheet() {
        return this.reportService.getBalanceSheet();
    }

    @GetMapping("/net-income")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public NetIncomeDto getNetIncome() {
        return this.reportService.getNetIncome();
    }
}

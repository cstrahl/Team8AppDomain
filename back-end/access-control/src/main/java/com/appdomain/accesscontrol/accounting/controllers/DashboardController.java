package com.appdomain.accesscontrol.accounting.controllers;

import com.appdomain.accesscontrol.accounting.contracts.RatioDto;
import com.appdomain.accesscontrol.accounting.services.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(final DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/profit-margin")
    public RatioDto getProfitMargin() {
        return this.dashboardService.calculateProfitMargin();
    }

    @GetMapping("/on-assets")
    public RatioDto getReturnOnAssets() {
        return this.dashboardService.calculateReturnOnAssets();
    }

    @GetMapping("/on-equity")
    public RatioDto getReturnOnEquity() {
        return this.dashboardService.calculateReturnOnEquity();
    }

    @GetMapping("/current-ratio")
    public RatioDto getCurrentRatio() {
        return this.dashboardService.calculateCurrentRatio();
    }

    @GetMapping("/quick-ratio")
    public RatioDto getQuickRatio() {
        return this.dashboardService.calculateQuickRatio();
    }

    @GetMapping("/inventory-ratio")
    public RatioDto getInventoryRatio() {
        return this.dashboardService.calculateInventoryRatio();
    }

    @GetMapping("/debt-asset")
    public RatioDto getDebtAssetRatio() {
        return this.dashboardService.calculateDebtAssetRatio();
    }

    @GetMapping("/debt-equity")
    public RatioDto getDebtEquityRatio() {
        return this.dashboardService.calculateDebtEquityRatio();
    }

    @GetMapping("/inventory-turnover")
    public RatioDto getInventoryTurnover() {
        return this.dashboardService.calculateInventoryTurnover();
    }

    @GetMapping("/asset-turnover")
    public RatioDto getAssetTurnover() {
        return this.dashboardService.calculateAssetTurnover();
    }
}

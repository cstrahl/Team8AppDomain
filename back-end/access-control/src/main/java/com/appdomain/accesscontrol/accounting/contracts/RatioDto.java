package com.appdomain.accesscontrol.accounting.contracts;

import com.appdomain.accesscontrol.accounting.utils.AlertLevel;

public class RatioDto {

    private double value;
    private AlertLevel alertLevel;

    public RatioDto() {
    }

    public RatioDto(final double value, final AlertLevel alertLevel) {
        this.value = value;
        this.alertLevel = alertLevel;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public AlertLevel getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(AlertLevel alertLevel) {
        this.alertLevel = alertLevel;
    }
}

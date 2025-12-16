package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Objects;

public record AccountSummaryWithDateRange(BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal net) {
    public AccountSummaryWithDateRange(BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal net) {
        this.totalIncome = Objects.requireNonNullElse(totalIncome, BigDecimal.ZERO);
        this.totalExpenses = Objects.requireNonNullElse(totalExpenses, BigDecimal.ZERO);
        this.net = Objects.requireNonNullElse(net, BigDecimal.ZERO);
    }
}
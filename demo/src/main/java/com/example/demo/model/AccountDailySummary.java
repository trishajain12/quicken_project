package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record AccountDailySummary(LocalDate date, BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal net) {
    public AccountDailySummary(LocalDate date, BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal net) {
        this.date = Objects.requireNonNull(date, "date cannot be null");
        this.totalIncome = Objects.requireNonNullElse(totalIncome, BigDecimal.ZERO);
        this.totalExpenses = Objects.requireNonNullElse(totalExpenses, BigDecimal.ZERO);
        this.net = Objects.requireNonNullElse(net, BigDecimal.ZERO);
    }
}
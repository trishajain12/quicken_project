package com.example.demo.model;

import java.math.BigDecimal;

public record AccountSummaryWithDateRange (
    BigDecimal totalIncome,
    BigDecimal totalExpenses,
    BigDecimal net
) {}
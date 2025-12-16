package com.example.demo.resources;

import java.math.BigDecimal;

public record AccountSummaryResource(BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal net) {
}

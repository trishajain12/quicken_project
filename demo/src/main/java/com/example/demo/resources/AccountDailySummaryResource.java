package com.example.demo.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountDailySummaryResource(LocalDate date, BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal net) {
}

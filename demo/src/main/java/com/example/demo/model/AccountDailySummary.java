// create the same thing as summary with range but this time add a date so it is repeated per day


package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountDailySummary (

        LocalDate date,
        BigDecimal totalIncome,
        BigDecimal totalExpenses,
        BigDecimal net
) {}
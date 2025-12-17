package com.example.demo.service;

import com.example.demo.resources.AccountDailySummaryResource;
import com.example.demo.resources.AccountResource;
import com.example.demo.resources.AccountSummaryResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class AccountServiceTest {
    @Autowired  AccountService accountService;

    @Test
    void listAccounts() {
        List<AccountResource> accounts = accountService.listAccounts();
        Assertions.assertEquals(3,accounts.size());
    }

    @Test
    void getAccountSummary_dateRangeWithIncomeExpenses() {

        long accountId = 1;
        LocalDate from = LocalDate.parse("2024-01-01");
        LocalDate to = LocalDate.parse("2024-12-31");

        AccountSummaryResource summary = accountService.getAccountSummary(accountId, from, to);

        assertMoneyEquals("7400.00", summary.totalIncome());
        assertMoneyEquals("3902.80", summary.totalExpenses());
        assertMoneyEquals("3497.20", summary.net());
    }

    @Test
    void getAccountSummary_boundaryDates() {
        long accountId = 1;
        LocalDate from = LocalDate.parse("2024-01-01");
        LocalDate to = LocalDate.parse("2024-01-01");

        AccountSummaryResource summary = accountService.getAccountSummary(accountId, from, to);

        assertMoneyEquals("3500.00", summary.totalIncome());
        assertMoneyEquals("0.00", summary.totalExpenses());
        assertMoneyEquals("3500.00", summary.net());
    }

    @Test
    void getAccountSummary_dateRangeWithNoTransactions() {
        long accountId = 1;
        LocalDate from = LocalDate.parse("2023-01-01");
        LocalDate to = LocalDate.parse("2023-12-31");

        AccountSummaryResource summary = accountService.getAccountSummary(accountId, from, to);

        assertMoneyEquals("0.00", summary.totalIncome());
        assertMoneyEquals("0.00", summary.totalExpenses());
        assertMoneyEquals("0.00", summary.net());
    }

    @Test
    void getDailySummary_boundaryDays() {
        long accountId = 1;
        LocalDate from = LocalDate.parse("2024-01-01");
        LocalDate to = LocalDate.parse("2024-01-02");

        List<AccountDailySummaryResource> daily = accountService.getDailySummary(accountId, from, to);

        Assertions.assertEquals(2,daily.size());

        Assertions.assertEquals(LocalDate.parse("2024-01-01"), daily.get(0).date());
        assertMoneyEquals("3500.00", daily.get(0).totalIncome());
        assertMoneyEquals("0.00", daily.get(0).totalExpenses());
        assertMoneyEquals("3500.00", daily.get(0).net());

        Assertions.assertEquals(LocalDate.parse("2024-01-02"), daily.get(1).date());
        assertMoneyEquals("0.00", daily.get(1).totalIncome());
        assertMoneyEquals("1500.00", daily.get(1).totalExpenses());
        assertMoneyEquals("-1500.00", daily.get(1).net());
    }

    @Test
    void getDailySummary_noTransaction() {
        long accountId = 1;
        LocalDate from = LocalDate.parse("2023-01-01");
        LocalDate to = LocalDate.parse("2023-01-31");

        List<AccountDailySummaryResource> daily = accountService.getDailySummary(accountId, from, to);

        Assertions.assertTrue(daily.isEmpty());
    }

    @Test
    void getAccountSummary_noLeakAccounts() {
        LocalDate from = LocalDate.parse("2024-01-01");
        LocalDate to = LocalDate.parse("2024-01-31");

        AccountSummaryResource account_1 = accountService.getAccountSummary(1, from, to);
        assertMoneyEquals("3900.00", account_1.totalIncome());
        assertMoneyEquals("2101.75", account_1.totalExpenses());
        assertMoneyEquals("1798.25", account_1.net());

        AccountSummaryResource account_2 = accountService.getAccountSummary(2, from, to);
        assertMoneyEquals("6800.00", account_2.totalIncome());
        assertMoneyEquals("1976.25", account_2.totalExpenses());
        assertMoneyEquals("4823.75", account_2.net());

        Assertions.assertEquals(0, account_1.totalIncome().compareTo(new BigDecimal("3900.00")));
    }

    private void assertMoneyEquals(String expected, BigDecimal actual) {
        Assertions.assertNotNull(actual, "Money value was null");
        Assertions.assertEquals(0, actual.compareTo(new BigDecimal(expected)),
                "Expected " + expected + " but got " + actual);
    }
}
package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.AccountDailySummary;
import com.example.demo.model.AccountSummaryWithDateRange;
import com.example.demo.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/accounts")
        public List<Account> getAllAccounts(){
            return accountService.listAccounts();
        }

    @GetMapping("/api/accounts/{accountId}/summary")
    public AccountSummaryWithDateRange getAccountSummary(
            @PathVariable long accountId,
            @RequestParam String from,
            @RequestParam String to) {

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        return accountService.getAccountSummary(accountId, fromDate, toDate);
    }

    @GetMapping("/api/accounts/{accountId}/daily-summary")
    public List<AccountDailySummary> getDailySummary (
            @PathVariable long accountId,
            @RequestParam String from,
            @RequestParam String to) {

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        return accountService.getDailySummary(accountId, fromDate, toDate);
    }
}
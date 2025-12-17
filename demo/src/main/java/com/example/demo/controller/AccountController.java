package com.example.demo.controller;

import com.example.demo.resources.AccountDailySummaryResource;
import com.example.demo.resources.AccountResource;
import com.example.demo.resources.AccountSummaryResource;
import com.example.demo.service.AccountService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResource> getAllAccounts(){
        return accountService.listAccounts();
    }

    @GetMapping("/{accountId}/summary")
    public AccountSummaryResource getAccountSummary(
            @PathVariable long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to)
    {
        checkAccountValue(accountId);
        checkDateValues(from,to);
        checkAccountExists(accountId);

        return accountService.getAccountSummary(accountId, from, to);
    }

    @GetMapping("/{accountId}/daily-summary")
    public List<AccountDailySummaryResource> getDailySummary (
            @PathVariable long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to)
    {
        checkAccountValue(accountId);
        checkDateValues(from,to);
        checkAccountExists(accountId);

        return accountService.getDailySummary(accountId, from, to);
    }

    private void checkAccountValue(long accountId) {
        if (accountId <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"accountId must be a positive integer 1 and above");
    }
    private void checkDateValues(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "from must be on or before to");
        }
    }

    private void checkAccountExists(long accountId) {
        if (!accountService.accountExists(accountId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

}
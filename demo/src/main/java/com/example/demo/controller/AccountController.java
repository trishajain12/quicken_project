package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.AccountDailySummary;
import com.example.demo.model.AccountSummaryWithDateRange;
import com.example.demo.resources.AccountDailySummaryResource;
import com.example.demo.resources.AccountResource;
import com.example.demo.resources.AccountSummaryResource;
import com.example.demo.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/accounts")
        public List<AccountResource> getAllAccounts(){
            return accountService.listAccounts();
        }

    @GetMapping("/api/accounts/{accountId}/summary")
    public AccountSummaryResource getAccountSummary(
            @PathVariable long accountId,
            @RequestParam String from,
            @RequestParam String to)
    {
        checkAccountValue(accountId);
        if (from == null || to == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "We a value for both to and from.");

        LocalDate fromDate;
        LocalDate toDate;
        try {
            fromDate = LocalDate.parse(from);
            toDate = LocalDate.parse(to);
        } catch (Exception e)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format - use YYYY-MM-DD.");
        }

        checkDateValues(fromDate,toDate);

        return accountService.getAccountSummary(accountId, fromDate, toDate);
    }


    @GetMapping("/api/accounts/{accountId}/daily-summary")
    public List<AccountDailySummaryResource> getDailySummary (
            @PathVariable long accountId,
            @RequestParam String from,
            @RequestParam String to)
    {
        checkAccountValue(accountId);

        LocalDate fromDate;
        LocalDate toDate;
        try {
            fromDate = LocalDate.parse(from);
            toDate = LocalDate.parse(to);
        } catch (Exception e)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format - use YYYY-MM-DD.");
        }

        checkDateValues(fromDate,toDate);

        return accountService.getDailySummary(accountId, fromDate, toDate);
    }

    private void checkAccountValue(long accountId) {
        if (accountId <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"We need a proper account natural number 0 and onwards.");
    }
    private void checkDateValues(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From is after to - recheck the date values");
        }
    }
}
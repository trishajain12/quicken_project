package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.AccountDailySummary;
import com.example.demo.model.AccountSummaryWithDateRange;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AccountRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<Account> listAccounts(){
        return accountRepository.findAllAccounts();
    }

    public AccountSummaryWithDateRange getAccountSummary(long accountId, LocalDate from, LocalDate to) {
        return accountRepository.getAccountSummary(accountId, from, to);
    }

    public List<AccountDailySummary> getDailySummary(long accountId, LocalDate from, LocalDate to) {
        return accountRepository.getDailySummary(accountId, from, to);
    }
}



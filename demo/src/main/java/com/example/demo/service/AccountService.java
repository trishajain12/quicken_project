package com.example.demo.service;

import com.example.demo.mapper.AccountDailySummaryMapper;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.AccountSummaryMapper;
import com.example.demo.resources.AccountDailySummaryResource;
import com.example.demo.resources.AccountResource;
import com.example.demo.resources.AccountSummaryResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AccountRepository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountSummaryMapper accountSummaryMapper;
    private final AccountDailySummaryMapper accountDailySummaryMapper;

    public AccountService(
            AccountRepository accountRepository,
            AccountMapper accountMapper,
            AccountSummaryMapper accountSummaryMapper,
            AccountDailySummaryMapper accountDailySummaryMapper
    ) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.accountSummaryMapper = accountSummaryMapper;
        this.accountDailySummaryMapper = accountDailySummaryMapper;
    }

    public List<AccountResource> listAccounts(){
        return accountMapper.toDtoList(accountRepository.findAllAccounts());
    }

    public AccountSummaryResource getAccountSummary(long accountId, LocalDate from, LocalDate to) {
        requireAccountExists(accountId);
        return accountSummaryMapper.toDto(accountRepository.getAccountSummary(accountId, from, to));
    }

    public List<AccountDailySummaryResource> getDailySummary(long accountId, LocalDate from, LocalDate to) {
        requireAccountExists(accountId);
        return accountDailySummaryMapper.toDtoList(accountRepository.getDailySummary(accountId, from, to));
    }

    private void requireAccountExists(long accountId) {
        if (!accountRepository.accountExists(accountId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}

package com.example.demo.service;
import com.example.demo.model.Account;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AccountRepository;
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
}



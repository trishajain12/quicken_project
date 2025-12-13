package com.example.demo.service;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AccountRepository;
import java.util.List;

@Service
// business logic
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<String> listAccounts(){
        System.out.println("Service is working calling repo!");
        return accountRepository.findAllAccounts();

    }

}



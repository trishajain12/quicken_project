package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
// this is what will talk with my database
public class AccountRepository {
    public List<String> findAllAccounts(){
        System.out.println("Repository is working will use a temp list.");
        return List.of("Account_1","Account_2", "Account_3");
    }
}

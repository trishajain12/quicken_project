package com.example.demo.controller;

import com.example.demo.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
// HTTP requests

public class AccountController {

    private final AccountService accountService;

    // inversion of control + DI - spring knows what to inject, no need to use
    // new... because that causes high coupling we want low
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/accounts")
    //GET /api/accounts --> sends request --> controller --> service and then repository will return a list to service to controller to json list
        public List<String> getAllAccounts(){
            System.out.println("Controller: we have done it!");
            return accountService.listAccounts();
        }
}

//    @GetMapping("/testing")
//    public String testing() {
//        return "Rest Controller is Working - All Good";
//    }

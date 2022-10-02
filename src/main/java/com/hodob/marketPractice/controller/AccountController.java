package com.hodob.marketPractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.hodob.marketPractice.domain.Account;
import com.hodob.marketPractice.service.AccountService;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{username}/{password}/{role}")
    public Account createAccount(
            @ModelAttribute
                    Account account
    ) {
        return accountService.save(account);
    }
}

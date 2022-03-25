package com.othmane.simplebank.controller;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>> getAccounts() {
        var accounts = this.accountRepository.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") String accountId) {
        var account = accountRepository.findById(accountId).orElse(null);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}

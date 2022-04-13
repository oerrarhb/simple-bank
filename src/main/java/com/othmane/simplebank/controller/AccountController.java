package com.othmane.simplebank.controller;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    private final AccountService accountService;

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>> getAccounts() {
        var accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") String accountId) {
        var account = accountService.getAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Boolean> deleteAccount(@PathVariable("id") String accountId) {
        if (accountService.deleteAccount(accountId)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);

    }

    @PostMapping("/addAccount")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        var accountSaved = accountService.addAccount(account);
        if (accountSaved != null) {
            return new ResponseEntity<>(accountSaved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

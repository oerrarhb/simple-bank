package com.othmane.simplebank.controller;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final OperationRepository operationRepository;
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;

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

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Boolean> deleteAccount(@PathVariable("id") String accountId) {
        var account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            updateOperations(accountId);
            updateClients(account.getClientId(), accountId);
            accountRepository.deleteById(accountId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @PostMapping("/addAccount")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        var client = clientRepository.findById(account.getClientId()).orElse(null);
        if (client != null) {
            var accountSaved = accountRepository.insert(account);
            client.getAccounts().add(accountSaved);
            mongoTemplate.save(client);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateClients(String clientId, String accountId) {
        var client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            client.getAccounts().removeIf(acc -> acc.getId().equals(accountId));
            mongoTemplate.save(client);
        }
    }

    private void updateOperations(String accountId) {
        var operations = operationRepository.findAll().stream().filter(op -> op.getAccountId().equals(accountId)).collect(Collectors.toList());
        operationRepository.deleteAll(operations);
    }
}

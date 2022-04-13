package com.othmane.simplebank.services;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final OperationRepository operationRepository;
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public boolean deleteAccount(String accountId) {
        var account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            updateOperations(accountId);
            updateClients(account.getClientId(), accountId);
            accountRepository.deleteById(accountId);
            return true;
        } else {
            return false;
        }
    }

    public Account addAccount(Account account) {
        var client = clientRepository.findById(account.getClientId()).orElse(null);
        if (client != null) {
            var accountSaved = accountRepository.insert(account);
            client.getAccounts().add(accountSaved);
            mongoTemplate.save(client);
            return accountSaved;
        } else {
            return null;
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

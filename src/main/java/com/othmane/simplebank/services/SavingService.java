package com.othmane.simplebank.services;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.model.Operation;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingService {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    public SavingService(OperationRepository operationRepository, AccountRepository accountRepository, ClientRepository clientRepository) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }


    private List<Operation> findOperationByAccountId(String accountId) {
        return operationRepository.findAll().stream().filter(op -> op.getAccountId().equals(accountId)).collect(Collectors.toList());
    }

    private List<Account> findAccountByClientId(String clientId) {
        return accountRepository.findAll().stream().filter(acc -> acc.getClientId().equals(clientId)).collect(Collectors.toList());
    }

    private void saveAccount(Account account) {
        try {
            var accountSaved = accountRepository.save(account);
            for (var operation : account.getOperationList()) {
                operation.setAccountId(accountSaved.getId());
                operationRepository.save(operation);
            }
            accountSaved.setOperationList(findOperationByAccountId(accountSaved.getId()));
            accountRepository.save(account);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void saveClient(Client client) {
        try {
            var clientSaved = clientRepository.save(client);
            for (Account account : client.getAccounts()) {
                account.setClientId(clientSaved.getId());
                saveAccount(account);
            }
            clientSaved.setAccounts(findAccountByClientId(clientSaved.getId()));
            clientRepository.save(clientSaved);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}

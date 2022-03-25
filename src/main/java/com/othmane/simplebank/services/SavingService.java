package com.othmane.simplebank.services;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    private void saveAccount(Account account) {
        try {
            var accountSaved = accountRepository.save(account);
            for (var operation : account.getOperationList()) {
                operation.setAccountId(accountSaved.getId());
                operationRepository.save(operation);
            }
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}

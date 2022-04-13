package com.othmane.simplebank.services;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.model.Operation;
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
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final OperationRepository operationRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(String clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    public boolean deleteClient(String clientId) {
        var client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            updateAccountsAndOperations(clientId);
            clientRepository.deleteById(clientId);
            return true;
        }
        return false;
    }

    public Client addClient(Client client) {
        for (Account account : client.getAccounts()) {
            for (Operation operation : account.getOperationList()) {
                operationRepository.insert(operation);
            }
            accountRepository.insert(account);
        }
        return clientRepository.insert(client);
    }

    public Client updateClient(String clientId, Client client) {
        var clientInDB = clientRepository.findById(clientId);
        if (clientInDB.isPresent()) {
            var clientToMerge = clientInDB.get();
            mergeOnUpdate(clientToMerge, client);
            return clientToMerge;
        }
        return null;
    }


    private void mergeOnUpdate(Client clientInDB, Client client) {
        if (client.getFirstName() != null) {
            clientInDB.setFirstName(client.getFirstName());
        }
        if (client.getLastName() != null) {
            clientInDB.setLastName(client.getLastName());
        }
        if (client.getPhoneNumber() != null) {
            clientInDB.setPhoneNumber(client.getPhoneNumber());
        }
        if (client.getEmail() != null) {
            clientInDB.setEmail(client.getEmail());
        }
        if (client.getAddress() != null) {
            clientInDB.setAddress(client.getAddress());
        }
        mongoTemplate.save(clientInDB);
    }

    private void updateAccountsAndOperations(String clientId) {
        var accounts = accountRepository
                .findAll()
                .stream()
                .filter(acc -> acc.getClientId().equals(clientId))
                .collect(Collectors.toList());
        var setOfIds = accounts.stream().map(Account::getId).collect(Collectors.toSet());
        var operations = operationRepository
                .findAll()
                .stream()
                .filter(op -> setOfIds.contains(op.getAccountId()))
                .collect(Collectors.toList());
        accountRepository.deleteAll(accounts);
        operationRepository.deleteAll(operations);
    }

}

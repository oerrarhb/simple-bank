package com.othmane.simplebank.controller;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.model.Operation;
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
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final OperationRepository operationRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;


    @GetMapping("/getAllClients")
    public ResponseEntity<List<Client>> getClients() {
        var clients = clientRepository.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/getClient/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") String clientId) {
        var client = clientRepository.findById(clientId).orElse(null);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable("id") String clientId) {
        var client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            updateAccountsAndOperations(clientId);
            clientRepository.deleteById(clientId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PostMapping("/addClient")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        for (Account account : client.getAccounts()) {
            for (Operation operation : account.getOperationList()) {
                operationRepository.insert(operation);
            }
            accountRepository.insert(account);
        }
        return new ResponseEntity<>(clientRepository.insert(client), HttpStatus.OK);
    }

    @PutMapping("/updateClient/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") String clientId, @RequestBody Client client) {
        var clientInDB = clientRepository.findById(clientId);
        if (clientInDB.isPresent()) {
            var clientToMerge = clientInDB.get();
            mergeOnUpdate(clientToMerge, client);
            return new ResponseEntity<>(clientToMerge, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

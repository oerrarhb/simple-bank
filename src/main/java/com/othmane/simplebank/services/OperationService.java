package com.othmane.simplebank.services;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Operation;
import com.othmane.simplebank.model.OperationType;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OperationService {

    @Autowired
    private final OperationRepository operationRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public Operation getOperationById(String operationId) {
        return operationRepository.findById(operationId).orElse(null);
    }

    public boolean deleteOperation(String operationId) {
        var operation = operationRepository.findById(operationId).orElse(null);
        if (operation != null) {
            updateAccountOnDelete(operationId, operation.getAccountId());
        }
        operationRepository.deleteById(operationId);
        return operationRepository.findById(operationId).isEmpty();
    }

    public Operation addOperation(Operation operation) {
        if (isOperationTypeCoherent(operation)) {
            var savedOperation = operationRepository.insert(operation);
            var accountExist = updateAccountOnPost(savedOperation);
            return accountExist ? savedOperation : null;
        } else {
            return null;
        }
    }

    private boolean isOperationTypeCoherent(Operation operation) {
        var type = operation.getOperationType();
        return type.equals(OperationType.CREDIT) || type.equals(OperationType.DEBIT);
    }

    private boolean updateAccountOnPost(Operation operation) {
        var account = accountRepository.findById(operation.getAccountId()).orElse(null);
        if (account != null) {
            if (operation.getOperationType().equals(OperationType.CREDIT)) {
                account.setBalance(account.getBalance() + operation.getTransactionAmount());
            } else {
                account.setBalance(account.getBalance() - operation.getTransactionAmount());
            }
            account.getOperationList().add(operation);
            mongoTemplate.save(account);
            updateClient(account);
            return true;
        }
        return false;
    }


    private void updateAccountOnDelete(String operationId, String accountId) {
        var account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            account.getOperationList().removeIf(op -> op.getId().equals(operationId));
            mongoTemplate.save(account);
            updateClient(account);
        }
    }


    private void updateClient(Account account) {
        var client = clientRepository.findById(account.getClientId()).orElse(null);
        if (client != null) {
            client.getAccounts().removeIf(acc -> acc.getId().equals(account.getId()));
            client.getAccounts().add(account);
            mongoTemplate.save(client);
        }
    }
}

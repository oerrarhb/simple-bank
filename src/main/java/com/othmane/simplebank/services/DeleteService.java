package com.othmane.simplebank.services;

import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteService {

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final OperationRepository operationRepository;

    public void purge() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        operationRepository.deleteAll();
    }
}

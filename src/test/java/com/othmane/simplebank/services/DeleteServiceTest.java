package com.othmane.simplebank.services;

import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class DeleteServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private OperationRepository operationRepository;

    private DeleteService deleteService;

    @BeforeEach
    void setUp() {
        deleteService = new DeleteService(accountRepository, clientRepository, operationRepository);
    }

    @DisplayName("Test all beans deleting")
    @Test
    public void canDeleteAll() {
        deleteService.purge();
        verify(accountRepository).deleteAll();
        verify(clientRepository).deleteAll();
        verify(operationRepository).deleteAll();
    }
}
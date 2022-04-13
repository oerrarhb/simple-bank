package com.othmane.simplebank.services;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SavingServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private OperationRepository operationRepository;

    private SavingService savingService;

    @BeforeEach
    void setUp() {
        savingService = new SavingService(accountRepository, clientRepository, operationRepository);
    }


    @DisplayName("Find Operation by Account id test")
    @Test
    public void canFindOperationByAccountById() {
        savingService.findOperationByAccountId("1235");
        verify(operationRepository).findAll();
    }

    @DisplayName("Find Account by Client id test")
    @Test
    public void canFindAccountByClientById() {
        savingService.findAccountByClientId("1233");
        verify(accountRepository).findAll();
    }


    @DisplayName("Save Account Test")
    @Test
    public void canSaveAccount() {
        var account = Account.builder()
                .id("1234")
                .balance(10000.00)
                .ClientId("112255")
                .operationList(new ArrayList<>())
                .build();
        savingService.saveAccount(account);
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountArgumentCaptor.capture());
        assertEquals(accountArgumentCaptor.getValue(), account);
    }


    @DisplayName("Save Client Test")
    @Test
    public void canSaveClient() {
        var client = Client.builder()
                .id("1234")
                .firstName("Daryl")
                .lastName("DIXON")
                .phoneNumber("00 55 88 66 77")
                .email("dd@email.com")
                .address("73 Street Of the Walking Dead Alexandria")
                .accounts(new ArrayList<>())
                .build();
        savingService.saveClient(client);
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        assertEquals(clientArgumentCaptor.getValue(), client);
    }
}
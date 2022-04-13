package com.othmane.simplebank.services.util;

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
        var account = new Account();
        account.setId("1234");
        account.setBalance(10000.00);
        account.setClientId("112255");
        account.setOperationList(new ArrayList<>());
        savingService.saveAccount(account);
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountArgumentCaptor.capture());
        assertEquals(accountArgumentCaptor.getValue(), account);
    }


    @DisplayName("Save Client Test")
    @Test
    public void canSaveClient() {
        var client = new Client();
        client.setId("1234");
        client.setFirstName("Daryl");
        client.setLastName("DIXON");
        client.setPhoneNumber("00 55 88 66 77");
        client.setEmail("dd@email.com");
        client.setAddress("73 Street Of the Walking Dead Alexandria");
        client.setAccounts(new ArrayList<>());

        savingService.saveClient(client);
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        assertEquals(clientArgumentCaptor.getValue(), client);
    }
}
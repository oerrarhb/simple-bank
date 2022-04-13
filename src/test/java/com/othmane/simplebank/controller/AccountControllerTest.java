package com.othmane.simplebank.controller;

import com.othmane.simplebank.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountController = new AccountController(accountService);
    }

    @DisplayName("Test get all accounts")
    @Test
    public void canGetAllAccounts() {

    }


    @DisplayName("Test get account by id")
    @Test
    public void canGetAccountById() {

    }

    @DisplayName("Test delete account by id")
    @Test
    public void canDeleteAccountById() {

    }

    @DisplayName("Test add account")
    @Test
    public void canAddAccount() {

    }


}
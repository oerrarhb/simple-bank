package com.othmane.simplebank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private final String ClientId;
    private final double balance;

    public Account(String clientId, double balance) {
        ClientId = clientId;
        this.balance = balance;
    }
}

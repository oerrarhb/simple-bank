package com.othmane.simplebank.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "clients")
public class Client {
    @Id
    private String id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;
    private final String address;
    private final List<Account> accounts;

    public Client(String firstName, String lastName, String phoneNumber, String email, String address, List<Account> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.accounts = accounts;
    }
}

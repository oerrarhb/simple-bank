package com.othmane.simplebank.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "clients")
public class Client {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private List<Account> accounts;

    public Client(String firstName, String lastName, String phoneNumber, String email, String address, List<Account> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.accounts = accounts;
    }
}

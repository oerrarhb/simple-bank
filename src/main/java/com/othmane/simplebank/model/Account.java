package com.othmane.simplebank.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private String ClientId;
    private double balance;
    private List<Operation> operationList;

    public Account(double balance, List<Operation> operationList) {
        this.balance = balance;
        this.operationList = operationList;
    }
}

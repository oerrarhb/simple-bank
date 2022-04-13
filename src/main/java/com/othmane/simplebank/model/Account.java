package com.othmane.simplebank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
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

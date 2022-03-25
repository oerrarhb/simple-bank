package com.othmane.simplebank.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "operations")
public class Operation {
    @Id
    private String id;
    private String accountId;
    private final OperationType operationType;
    private final double transactionAmount;

    public Operation(OperationType operationType, double transactionAmount) {
        this.operationType = operationType;
        this.transactionAmount = transactionAmount;
    }
}

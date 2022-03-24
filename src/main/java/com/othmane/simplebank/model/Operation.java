package com.othmane.simplebank.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
public class Operation {
    @Id
    private String id;
    private final OperationType operationType;
    private final double transactionAmount;
}

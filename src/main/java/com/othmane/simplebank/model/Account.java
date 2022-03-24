package com.othmane.simplebank.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
public class Account {
    @Id
    private String id;
    private final Long ClientId;
    private final double balance;
}

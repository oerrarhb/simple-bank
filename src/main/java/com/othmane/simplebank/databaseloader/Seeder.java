package com.othmane.simplebank.databaseloader;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.model.Operation;
import com.othmane.simplebank.model.OperationType;
import com.othmane.simplebank.services.SavingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Seeder {

    private final SavingService savingService;

    public Seeder(SavingService savingService) {
        this.savingService = savingService;
    }


    public void save() {
        var daryClient = new Client("Daryl",
                "Dixon",
                "777-444-888",
                "dd@outlook.com",
                "Atlanta",
                List.of(new Account(100000.00,
                        List.of(new Operation(OperationType.CREDIT, 20000),
                                new Operation(OperationType.CREDIT, 5000)))));
        savingService.saveClient(daryClient);
    }
}
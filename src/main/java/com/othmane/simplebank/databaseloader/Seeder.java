package com.othmane.simplebank.databaseloader;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.model.Operation;
import com.othmane.simplebank.model.OperationType;
import com.othmane.simplebank.services.util.PurgeService;
import com.othmane.simplebank.services.util.SavingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class Seeder {

    private final SavingService savingService;
    private final PurgeService deleteService;

    public void save() {
        var darylDixon = new Client("Daryl",
                "Dixon",
                "777-444-888",
                "dd@outlook.com",
                "Atlanta",
                List.of(new Account(100000.00,
                        List.of(new Operation(OperationType.CREDIT, 20000),
                                new Operation(OperationType.CREDIT, 5000)))));
        savingService.saveClient(darylDixon);


        var jhonWick = new Client("Jhon",
                "Wick",
                "899-444-888",
                "jw@outlook.com",
                "New-York",
                List.of(new Account(900000.00,
                        List.of(new Operation(OperationType.CREDIT, 20000),
                                new Operation(OperationType.DEBIT, 5000)))));
        savingService.saveClient(jhonWick);

    }

    public void deleteAll() {
        deleteService.purge();
    }

}

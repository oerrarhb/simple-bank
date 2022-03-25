package com.othmane.simplebank;

import com.othmane.simplebank.databaseloader.Seeder;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import com.othmane.simplebank.services.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimplebankApplication implements CommandLineRunner {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SimplebankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var databaseLoader = new Seeder(new SavingService(operationRepository, accountRepository, clientRepository));
        databaseLoader.save();
    }
}

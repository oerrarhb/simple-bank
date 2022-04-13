package com.othmane.simplebank;

import com.othmane.simplebank.databaseloader.Seeder;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import com.othmane.simplebank.repositories.OperationRepository;
import com.othmane.simplebank.services.util.PurgeService;
import com.othmane.simplebank.services.util.SavingService;
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
        var purgeService = new PurgeService(accountRepository, clientRepository, operationRepository);
        var savingService = new SavingService(accountRepository, clientRepository, operationRepository);
        var databaseLoader = new Seeder(savingService, purgeService);
        purgeService.purge();
        databaseLoader.save();
    }
}

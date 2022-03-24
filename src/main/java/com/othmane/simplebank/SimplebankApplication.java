package com.othmane.simplebank;

import com.othmane.simplebank.model.Account;
import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.model.Operation;
import com.othmane.simplebank.repositories.AccountRepository;
import com.othmane.simplebank.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SimplebankApplication implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(SimplebankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting App .........");

        var account = new Account("id-1234", 50.40);
        accountRepository.save(account);

        var client = new Client("Daryl", "Dixon", "777-444-333", "dd@outlook.com", "Atlanta", List.of(account));
        clientRepository.save(client);
    }
}

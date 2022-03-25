package com.othmane.simplebank.controller;

import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/getAllClients")
    public ResponseEntity<List<Client>> getClients() {
        var clients = this.clientRepository.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/getClient/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") String clientId) {
        var client = clientRepository.findById(clientId).orElse(null);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
}

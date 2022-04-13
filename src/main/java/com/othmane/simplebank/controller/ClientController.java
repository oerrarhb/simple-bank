package com.othmane.simplebank.controller;


import com.othmane.simplebank.model.Client;
import com.othmane.simplebank.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;


    @GetMapping("/getAllClients")
    public ResponseEntity<List<Client>> getClients() {
        var clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/getClient/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") String clientId) {
        var client = clientService.getClientById(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable("id") String clientId) {
        if (clientService.deleteClient(clientId)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PostMapping("/addClient")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.OK);
    }

    @PutMapping("/updateClient/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") String clientId, @RequestBody Client client) {
        var clientUpdated = clientService.updateClient(clientId, client);
        if (clientUpdated != null) {
            return new ResponseEntity<>(clientUpdated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

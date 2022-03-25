package com.othmane.simplebank.controller;

import com.othmane.simplebank.model.Operation;
import com.othmane.simplebank.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/operations")
public class OperationController {
    @Autowired
    private final OperationRepository operationRepository;

    public OperationController(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @GetMapping("/getAllOperations")
    public ResponseEntity<List<Operation>> getOperations() {
        var operations = this.operationRepository.findAll();
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }
}

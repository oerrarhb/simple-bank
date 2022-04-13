package com.othmane.simplebank.controller;

import com.othmane.simplebank.model.Operation;
import com.othmane.simplebank.services.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/operations")
public class OperationController {


    private final OperationService operationService;

    @GetMapping("/getAllOperations")
    public ResponseEntity<List<Operation>> getOperations() {
        return new ResponseEntity<>(operationService.getAllOperations(), HttpStatus.OK);
    }

    @GetMapping("/getOperation/{id}")
    public ResponseEntity<Operation> getOperation(@PathVariable("id") String operationId) {
        return new ResponseEntity<>(operationService.getOperationById(operationId), HttpStatus.OK);
    }

    @DeleteMapping("/deleteOperation/{id}")
    public ResponseEntity<Boolean> deleteOperation(@PathVariable("id") String operationId) {
        return new ResponseEntity<>(operationService.deleteOperation(operationId), HttpStatus.OK);
    }

    @PostMapping("/addOperation")
    public ResponseEntity<Operation> addOperation(@RequestBody Operation operation) {
        var savedOperation = operationService.addOperation(operation);
        return savedOperation != null ? new ResponseEntity<>(savedOperation, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}

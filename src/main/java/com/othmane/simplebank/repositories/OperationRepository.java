package com.othmane.simplebank.repositories;

import com.othmane.simplebank.model.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OperationRepository extends MongoRepository<Operation, String> {
}

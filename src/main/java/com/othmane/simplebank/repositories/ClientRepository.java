package com.othmane.simplebank.repositories;

import com.othmane.simplebank.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {

}

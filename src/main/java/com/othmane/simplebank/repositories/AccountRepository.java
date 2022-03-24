package com.othmane.simplebank.repositories;

import com.othmane.simplebank.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

}

package ca.jent.bank.customerapi.repositories;

import ca.jent.bank.customerapi.data.Customer;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableReactiveMongoRepositories
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {}

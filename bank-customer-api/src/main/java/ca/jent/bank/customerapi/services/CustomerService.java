package ca.jent.bank.customerapi.services;

import ca.jent.bank.customerapi.data.Customer;
import ca.jent.bank.customerapi.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Mono<Customer> getCustomer(String identifier) {
        return customerRepository.findById(identifier);
    }

    public Flux<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Mono<Customer> saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Void> deleteCustomer(String identifier) {
        return customerRepository.deleteById(identifier);
    }
}

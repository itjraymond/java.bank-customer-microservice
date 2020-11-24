package ca.jent.bank.customerapi.controllers;

import ca.jent.bank.customerapi.data.Customer;
import ca.jent.bank.customerapi.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    Mono<Customer> getCustomer(@PathVariable String id) {
        return customerService.getCustomer(id);
    }

    @GetMapping()
    Flux<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PutMapping("/{id}")
    Mono<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PostMapping()
    Mono<Customer> insertCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }
}

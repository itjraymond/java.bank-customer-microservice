package ca.jent.bank.customerapi.controllers;


import ca.jent.bank.customerapi.data.Customer;
import ca.jent.bank.customerapi.services.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class CustomerRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(CustomerService customerService) {
        return route()
                .GET(
                        "/functional/customers",
                        serverRequest -> ok().body(customerService.getCustomers(), Customer.class)
                )
                .GET(
                        "/functional/customers/{id}",
                        serverRequest -> ok().body(customerService.getCustomer(serverRequest.pathVariable("id")), Customer.class)
                )
                .PUT(
                        "/functional/customers/{id}",
                        serverRequest ->
                                serverRequest.bodyToMono(Customer.class)
                                             .flatMap(customerService::saveCustomer)
                                             .flatMap(customer -> ServerResponse.noContent().build())
                )
                .POST(
                        "/functional/customers",
                        serverRequest ->
                                serverRequest.bodyToMono(Customer.class)
                                    .flatMap(customerService::saveCustomer)
                                    .flatMap(customer ->
                                            created(URI.create("/functional/customers/" + customer.getId()))
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .body(BodyInserters.fromValue(customer))
                                    )
                )
                .POST(
                        "/functional2/customers",
                        request ->
                                request.bodyToMono(Customer.class)
                                       .flatMap(customerService::saveCustomer)
                                       .flatMap(customer ->
                                               created(URI.create("/functional2/customers/" + customer.getId())).build()
                                       )
                ).build();
    }
}



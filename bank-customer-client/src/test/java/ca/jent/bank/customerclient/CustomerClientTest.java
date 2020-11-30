package ca.jent.bank.customerclient;

import ca.jent.bank.customerclient.utils.LocalDateAdapter;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
class CustomerClientTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getCustomers_shouldReturnAFluxOfCustomerDTO() {
        var gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe()).create();

        List<CustomerDTO> customers = Arrays.asList(
                new CustomerDTO("1", "Jim", "Jones", LocalDate.of(1970, Month.AUGUST, 17)),
                new CustomerDTO("2", "Tim", "Taylor", LocalDate.of(1965, Month.JULY, 27)),
                new CustomerDTO("3", "Kim", "Kong", LocalDate.of(1983, Month.OCTOBER, 8))
        );

        var strJsonCustomers = customers
                .stream()
                .map(c -> gson.toJson(c, CustomerDTO.class))
                .collect(Collectors.joining(",", "[", "]"));

        // What works: (need double quotes for json properties not single quote
        // var str2 = "[ { \"id\":\"1\", \"firstname\":\"Jim\", \"lastname\":\"Jones\", \"dob\":\"1970-08-17\" }, { \"id\":\"2\", \"firstname\":\"Tim\", \"lastname\":\"Taylor\", \"dob\":\"1965-07-27\" }, { \"id\":\"3\", \"firstname\":\"Kim\",          \"lastname\":\"Kong\", \"dob\":\"1983-10-08\" }]";

        ClientResponse clientResponse = ClientResponse
                .create(HttpStatus.OK)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(strJsonCustomers)
                .build();

        // Registering the ExchangeFunction to the WebClient.Builder will essentially replace the "exchange()" method
        // call within our CustomerClient such that instead of making a remote call to get the response, it will simply
        // use our ExchangeFunction which simply return our ClientResponse defined above.
        ExchangeFunction exchangeFunction = request -> Mono.just(clientResponse);
        WebClient.Builder webClientBuilder = WebClient.builder().exchangeFunction(exchangeFunction);
        CustomerClient customerClient = new CustomerClient("http://fakeendpoint", webClientBuilder);
        Flux<CustomerDTO> fluxCustomers = customerClient.getCustomers();

        StepVerifier
                .create(fluxCustomers)
                .expectNext(
                        new CustomerDTO("1", "Jim", "Jones", LocalDate.of(1970, Month.AUGUST, 17)),
                        new CustomerDTO("2", "Tim", "Taylor", LocalDate.of(1965, Month.JULY, 27)),
                        new CustomerDTO("3", "Kim", "Kong", LocalDate.of(1983, Month.OCTOBER, 8))
                )
                .verifyComplete();

    }
}
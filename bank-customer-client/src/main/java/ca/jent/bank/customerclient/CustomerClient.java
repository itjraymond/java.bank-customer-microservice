package ca.jent.bank.customerclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CustomerClient {
    private final String endpoint;
    private final WebClient.Builder client;

    public Flux<CustomerDTO> getCustomers() {
        return client
                .baseUrl(endpoint)
                .build()
                .get()
                .uri("/customers")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(res -> res.bodyToFlux(CustomerDTO.class));
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CustomerDTO {
    private String id;
    private String firstname;
    private String lastname;
    private LocalDate dob;

}

package ru.kramskoi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kramskoi.dto.OwnerClientDTO;

public class HttpOwnerClientConf implements OwnerClient {
    @Qualifier("ownerWebClient")
    @Autowired
    private WebClient webClient;


    @Override
    public OwnerClientDTO getOwner(Long id) {
        return webClient
                .get()
                .uri("/owner/{id}", id)
                .retrieve()
                .bodyToMono(OwnerClientDTO.class)
                .block();
    }
}

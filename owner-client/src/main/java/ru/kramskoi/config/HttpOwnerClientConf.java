package ru.kramskoi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kramskoi.dto.OwnerMessage;

public class HttpOwnerClientConf implements OwnerClient {
    @Autowired
    private WebClient webClient;


    @Override
    public OwnerMessage getOwner(Long id) {
        return webClient
                .get()
                .uri("/owner/{id}", id)
                .retrieve()
                .bodyToMono(OwnerMessage.class)
                .block();
    }
}

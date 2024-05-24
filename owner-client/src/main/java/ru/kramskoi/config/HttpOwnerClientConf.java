package ru.kramskoi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kramskoi.dto.OwnerMessage;

public class HttpOwnerClientConf implements OwnerClient {
    @Autowired
    private WebClient webClient;

    @Override
    public Long addOwner(OwnerMessage owner) {
        return webClient
                .post()
                .uri("/owner")
                .body(Mono.just(owner), OwnerMessage.class)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }


    @Override
    public OwnerMessage getOwner(Long id) {
        return webClient
                .get()
                .uri("/owner/{id}", id)
                .retrieve()
                .bodyToMono(OwnerMessage.class)
                .block();
    }


    @Override
    public void updateOwner(OwnerMessage owner) {
        webClient
                .put()
                .uri("/owner/{id}", owner.getId())
                .body(Mono.just(owner), OwnerMessage.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }


    @Override
    public void deleteOwner(Long id) {
        webClient
                .delete()
                .uri("/owner/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}

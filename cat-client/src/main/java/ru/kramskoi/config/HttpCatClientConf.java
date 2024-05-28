package ru.kramskoi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatMessage;
import ru.kramskoi.dto.Color;

import java.util.List;

public class HttpCatClientConf implements CatClient {
    @Autowired
    private WebClient webClient;

    @Override
    public CatMessage getCatById(long id) {
        return webClient
                .get()
                .uri("/cat/%d".formatted(id))
                .retrieve()
                .bodyToMono(CatMessage.class)
                .block();
    }

    @Override
    public List<CatMessage> getFriendsById(long id) {
        return webClient
                .get()
                .uri("cat/%d/friends".formatted(id))
                .retrieve()
                .bodyToMono(List.class)
                .block();

    }

    @Override
    public List<CatMessage> getCatsByColorOrBreed(Color color, Breed breed) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("color", List.of(color.toString()));
        params.put("breed", List.of(breed.toString()));

        return webClient
                .get()
                .uri(it -> it.path("/cat").queryParams(params).build())
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    @Override
    public List<CatMessage> getAllCatsByOwnerId(long ownerId) {
        return webClient
                .get()
                .uri("/cat/%d".formatted(ownerId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatMessage>>() {
                })
                .block();
    }

}

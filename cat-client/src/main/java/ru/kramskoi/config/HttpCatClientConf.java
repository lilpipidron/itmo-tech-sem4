package ru.kramskoi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.Cat;
import ru.kramskoi.dto.Color;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class HttpCatClientConf implements CatClient {
    @Autowired
    private WebClient webClient;

    @Override
    public Cat getCatById(long id) {
        return webClient
                .get()
                .uri("/cat/%d".formatted(id))
                .retrieve()
                .bodyToMono(Cat.class)
                .block();
    }

    @Override
    public List<Cat> getFriendsById(long id) {
        return webClient
                .get()
                .uri("cat/%d/friends".formatted(id))
                .retrieve()
                .bodyToMono(List.class)
                .block();

    }

    @Override
    public List<Cat> getCatsByColorOrBreed(Color color, Breed breed) {
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
    public void updateCat(Cat cat) {
        webClient
                .put()
                .uri("/cat")
                .body(Mono.just(cat), Cat.class)
                .retrieve()
                .bodyToMono(Cat.class)
                .block();
    }

    @Override
    public void addFriend(long catId, long friendId) {
        Map<String, Long> requestBody = new HashMap<>();
        requestBody.put("catId", catId);
        requestBody.put("friendId", friendId);

        webClient
                .post()
                .uri("/cat/friends")
                .body(Mono.just(requestBody), Map.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void addCat(Cat cat) {
        webClient
                .post()
                .uri("/cat")
                .body(Mono.just(cat), Cat.class)
                .retrieve()
                .bodyToMono(Cat.class)
                .block();
    }

    @Override
    public List<Cat> getAllCatsByOwnerId(long ownerId) {
        return webClient
                .get()
                .uri("/cat/%d".formatted(ownerId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Cat>>() {})
                .block();
    }


    @Override
    public void deleteCat(long id) {
        webClient
                .delete()
                .uri("/cat/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}

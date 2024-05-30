package ru.kramskoi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.Color;

import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Optional;

public class HttpCatClient implements CatClient {
    @Qualifier("catWebClient")
    @Autowired
    private WebClient webClient;

    @Override
    public Optional<CatClientDTO> getCatById(Long id) {
        String url = "/cat/" + id;
        return Optional.ofNullable(webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(CatClientDTO.class)
                .block());
    }

    @Override
    public Optional<List<CatClientDTO>> getFriendsById(Long id) {
        return Optional.ofNullable(webClient
                .get()
                .uri("cat/%d/friends".formatted(id))
                .retrieve()
                .bodyToMono(List.class)
                .block());

    }


    @Override
    public Optional<List<CatClientDTO>> getCatsByColorOrBreedAndOwnerId(Color color, Breed breed, Long ownerId){
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("color", List.of(color.toString()));
        params.put("breed", List.of(breed.toString()));
        params.put("ownerId", List.of(String.valueOf(ownerId)));

        return Optional.ofNullable(webClient
                .get()
                .uri(it -> it.path("/cat").queryParams(params).build())
                .retrieve()
                .bodyToMono(List.class)
                .block());
    }

    @Override
    public Optional<List<CatClientDTO>> getAllCatsByOwnerId(Long ownerId) {
        return Optional.ofNullable(webClient
                .get()
                .uri("/cat/%d".formatted(ownerId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatClientDTO>>() {
                })
                .block());
    }

}

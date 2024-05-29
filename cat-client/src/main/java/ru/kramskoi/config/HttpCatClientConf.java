package ru.kramskoi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.Color;

import java.util.List;

public class HttpCatClientConf implements CatClient {
    @Autowired
    private WebClient webClient;

    @Override
    public CatClientDTO getCatById(long id) {
        return webClient
                .get()
                .uri("/cat/%d".formatted(id))
                .retrieve()
                .bodyToMono(CatClientDTO.class)
                .block();
    }

    @Override
    public List<CatClientDTO> getFriendsById(long id) {
        return webClient
                .get()
                .uri("cat/%d/friends".formatted(id))
                .retrieve()
                .bodyToMono(List.class)
                .block();

    }


    @Override
    public List<CatClientDTO> getCatsByColorOrBreedAndOwnerId(Color color, Breed breed, long ownerId){
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("color", List.of(color.toString()));
        params.put("breed", List.of(breed.toString()));
        params.put("ownerId", List.of(String.valueOf(ownerId)));

        return webClient
                .get()
                .uri(it -> it.path("/cat").queryParams(params).build())
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    @Override
    public List<CatClientDTO> getAllCatsByOwnerId(long ownerId) {
        return webClient
                .get()
                .uri("/cat/%d".formatted(ownerId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CatClientDTO>>() {
                })
                .block();
    }

}

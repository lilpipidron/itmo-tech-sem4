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
    public CatClientDTO getCatById(Long id) {
        return webClient
                .get()
                .uri("/cat/%d".formatted(id))
                .retrieve()
                .bodyToMono(CatClientDTO.class)
                .block();
    }

    @Override
    public List<CatClientDTO> getFriendsById(Long id) {
        return webClient
                .get()
                .uri("cat/%d/friends".formatted(id))
                .retrieve()
                .bodyToMono(List.class)
                .block();

    }


    @Override
    public List<CatClientDTO> getCatsByColorOrBreedAndOwnerId(Color color, Breed breed, Long ownerId) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (color != null) {
            params.put("color", List.of(color.toString()));
        }
        if (breed != null) {
            params.put("breed", List.of(breed.toString()));
        }
        if (ownerId != null) {
            params.put("ownerId", List.of(String.valueOf(ownerId)));
        }
        return webClient
                .get()
                .uri(it -> it.path("/cat").queryParams(params).build())
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    @Override
    public List<CatClientDTO> getAllCatsByOwnerId(Long ownerId) {
        return webClient
                .get()
                .uri("/cat/owner/%d".formatted(ownerId))
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

}

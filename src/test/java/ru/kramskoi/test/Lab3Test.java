package ru.kramskoi.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.dto.OwnerDTO;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static ru.kramskoi.breeds.Breed.BRITMAN;
import static ru.kramskoi.colors.Color.PINK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Lab3Test {
    private static RestTemplate restTemplate;

    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "");
    }

    @Test
    void should_create_user_with_id_1_and_get_him() {
        OwnerDTO owner = new OwnerDTO(1L, "lol", Date.valueOf("2006-01-02"));
        restTemplate.postForObject(baseUrl.concat("/owner"), owner, OwnerDTO.class);

        OwnerDTO ownerFromGetReq = restTemplate.getForObject(baseUrl.concat("/owner").concat("/1"), OwnerDTO.class);

        assertAll(
                () -> assertEquals(owner.getId(), ownerFromGetReq.getId()),
                () -> assertEquals(owner.getName(), ownerFromGetReq.getName()),
                () -> assertEquals(owner.getBirthday().toString(), ownerFromGetReq.getBirthday().toString())
        );
    }

    @Test
    void should_create_cat_with_id_1_and_get_him() {
        OwnerDTO owner = new OwnerDTO(1L, "lol", Date.valueOf("2006-01-02"));
        restTemplate.postForObject(baseUrl.concat("/owner"), owner, OwnerDTO.class);

        CatDTO cat = new CatDTO(1L, "lol", Date.valueOf("2006-01-02"), BRITMAN, PINK, 1L);
        restTemplate.postForObject(baseUrl.concat("/cat"), cat, CatDTO.class);

        CatDTO catFromGetReq = restTemplate.getForObject(baseUrl.concat("/cat").concat("/1"), CatDTO.class);
        assertNotNull(catFromGetReq);

        assertAll(
                () -> assertEquals(cat.getId(), catFromGetReq.getId()),
                () -> assertEquals(cat.getName(), catFromGetReq.getName()),
                () -> assertEquals(cat.getBirthday().toString(), catFromGetReq.getBirthday().toString()),
                () -> assertEquals(cat.getColor(), catFromGetReq.getColor()),
                () -> assertEquals(cat.getBreed(), catFromGetReq.getBreed()),
                () -> assertEquals(cat.getOwnerId(), catFromGetReq.getOwnerId())
        );
    }

    @Test
    void should_create_cat_with_id_1_and_delete_him() {
        OwnerDTO owner = new OwnerDTO(1L, "lol", Date.valueOf("2006-01-02"));
        restTemplate.postForObject(baseUrl.concat("/owner"), owner, OwnerDTO.class);

        CatDTO cat = new CatDTO(1L, "lol", Date.valueOf("2006-01-02"), BRITMAN, PINK, 1L);
        restTemplate.postForObject(baseUrl.concat("/cat"), cat, CatDTO.class);

        restTemplate.delete(baseUrl.concat("/cat").concat("/1"));

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restTemplate.getForObject(baseUrl.concat("/cat").concat("/1"), CatDTO.class);
        });
    }
}
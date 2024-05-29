package ru.kramskoi.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.kramskoi.ApplicationGateway;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Person;
import ru.kramskoi.repository.PersonRepository;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ApplicationGateway.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Lab3Test {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        Person person = new Person();
        person.setUsername("admin");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        person.setPassword(passwordEncoder.encode("admin"));
        person.setRoles("ADMIN");
        personRepository.save(person);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void shouldCreateOwner() throws Exception {
        OwnerDTO ownerDTO = new OwnerDTO(1L, "user", Date.valueOf("2006-01-02"), 1L);
        mvc.perform(post("/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ownerDTO)))
                .andExpect(status().isCreated());

        MvcResult result = mvc.perform(get("/owner/1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        OwnerDTO actualDTO = objectMapper.readValue(responseJson, OwnerDTO.class);

        assertAll(
                () -> assertEquals(ownerDTO.getId(), actualDTO.getId()),
                () -> assertEquals(ownerDTO.getName(), actualDTO.getName()),
                () -> assertEquals(ownerDTO.getBirthday().toString(), actualDTO.getBirthday().toString())
        );
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void shouldGetException_WhenTryToGetCat() throws Exception {
        OwnerDTO ownerDTO = new OwnerDTO(1L, "user", Date.valueOf("2006-01-02"), 1L);
        mvc.perform(post("/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ownerDTO)))
                .andExpect(status().isCreated());

        MvcResult result = mvc.perform(get("/cat/1"))
                .andExpect(status().isNotFound())
                .andReturn();

        String message = result.getResponse().getContentAsString();

        assertEquals("Cat not found", message);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void shouldGetException_WhenTryToGetOwner() throws Exception {
        MvcResult result = mvc.perform(get("/owner/1"))
                .andExpect(status().isNotFound())
                .andReturn();

        String message = result.getResponse().getContentAsString();

        assertEquals("Owner not found", message);
    }
}
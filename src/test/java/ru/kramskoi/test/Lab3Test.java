package ru.kramskoi.test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ru.kramskoi.ApplicationGateway;
import ru.kramskoi.CatApplication;
import ru.kramskoi.DomainApp;
import ru.kramskoi.OwnerApplication;

@SpringBootTest(classes = {ApplicationGateway.class, DomainApp.class, OwnerApplication.class, CatApplication.class}, properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application.properties")
public class Lab3Test {
/*
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void init() {
        Person admin = new Person();
        admin.setUsername("admin");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles("ADMIN");
        personRepository.save(admin);

        Person user = new Person();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles("USER");
        personRepository.save(user);
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
    public void shouldAddCat() throws Exception {
        OwnerDTO ownerDTO = new OwnerDTO(1L, "user", Date.valueOf("2006-01-02"), 1L);
        mvc.perform(post("/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ownerDTO)))
                .andExpect(status().isCreated());

        CatDTO catDTO = new CatDTO(1L, "Whiskers", Date.valueOf("2020-01-01"), Breed.BRITMAN, Color.WHITE, 1L);

        mvc.perform(post("/cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(catDTO)))
                .andExpect(status().isCreated());

        MvcResult result = mvc.perform(get("/cat/1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        CatDTO actualDTO = objectMapper.readValue(responseJson, CatDTO.class);

        System.out.println(actualDTO);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void shouldAddFriend() throws Exception {

        mvc.perform(post("/cat/1/friends/2"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void shouldGetException_WhenTryToGetCat() throws Exception {
        OwnerDTO ownerDTO =  new OwnerDTO(1L, "user", Date.valueOf("2006-01-02"), 1L);
        mvc.perform(post("/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ownerDTO)))
                .andExpect(status().isCreated());

        mvc.perform(get("http://locahost:3000/cat/1"))
                .andExpect(status().isInternalServerError());
    }*/
}

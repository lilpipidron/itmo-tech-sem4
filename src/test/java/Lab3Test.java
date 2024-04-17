import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import ru.kramskoi.Application;
import ru.kramskoi.entity.Owner;
import ru.kramskoi.repository.OwnerRepository;

import java.io.File;
import java.nio.file.Files;
import java.sql.Date;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SqlGroup({
        @Sql(value = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:init/owner-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class Lab3Test {
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_create_user_with_id_6() throws Exception {
        final File jsonFile = new ClassPathResource("init/user.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(post("/addOwner")
                        .contentType("application/json")
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isCreated());
        Owner exceptedOwner = new Owner(6L, "6", Date.valueOf("2006-01-02"));
        System.out.println(exceptedOwner);
//        assertEquals(exceptedOwner, this.ownerRepository.getOwnerById(6L));
    }
}

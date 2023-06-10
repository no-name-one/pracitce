package ru.ithub.nero.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.model.entity.User;
import ru.ithub.nero.repository.IUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
//@AutoConfigureRestDocs(outputDir = "target/snippets")
@Testcontainers
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private UserController userController;

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/snippets");

    @BeforeEach
    void setUpDb() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(provider))
                .build();
    }

    @Test
    public void getUsers_whenUsersExists_thenReturnUsers() throws Exception {

        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getUsers"))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        final List<UserDto> expectedUsersDto = new ArrayList<>();
        expectedUsersDto.add(new UserDto(1L, "Tom", 11, LocalDate.now()));
        expectedUsersDto.add(new UserDto(2L, "Jerry", 12, LocalDate.now()));
        expectedUsersDto.add(new UserDto(3L, "Carry", 13, LocalDate.now()));


        String entities = response.getResponse().getContentAsString();

        List<UserDto> actualUsersDto = objectMapper.readValue(entities, new TypeReference<List<UserDto>>() {});

        assertEquals(expectedUsersDto, actualUsersDto);
    }

    @Test
    public void getUser_whenUserExist_thenReturnUser() throws Exception {

        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.get("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getUser",
                        pathParameters(
                                parameterWithName("id").description("The id of the input to get")
                        )))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String entity = response.getResponse().getContentAsString();

        UserDto expectedUserDto = UserDto
                .builder()
                .id(1L)
                .name("Tom")
                .age(11)
                .date(LocalDate.now())
                .build();

        UserDto actualUserDto = objectMapper.readValue(entity, UserDto.class);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    @Transactional
    @Rollback
    public void createUser_whenUserDoesNotExist_thenReturnUser() throws Exception {
        CreateUserDto createUserDto = CreateUserDto
                .builder()
                .name("created user")
                .age(20)
                .build();

        String request = objectMapper.writeValueAsString(createUserDto);

        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.post("/user")
                        .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("createUser"))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String entity = response.getResponse().getContentAsString();

        UserDto expectedUserDto = UserDto
                .builder()
                .id(4L)
                .name("created user")
                .age(20)
                .date(LocalDate.now())
                .build();

        UserDto actualUserDto = objectMapper.readValue(entity, UserDto.class);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    public void updateUser_whenUserExist_thenReturnUser() throws Exception {
        UpdateUserDto updateUserDto = UpdateUserDto
                .builder()
                .name("updated user")
                .age(30)
                .build();

        String request = objectMapper.writeValueAsString(updateUserDto);

        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.put("/user/{id}", 3)
                        .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("updateUser",
                        pathParameters(
                                parameterWithName("id").description("The id of the input to update")
                        )))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String entity = response.getResponse().getContentAsString();

        UserDto expectedUserDto = UserDto
                .builder()
                .id(3L)
                .name("updated user")
                .age(30)
                .date(LocalDate.now())
                .build();

        UserDto actualUserDto = objectMapper.readValue(entity, UserDto.class);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUser_whenUserExist_thenReturnUser() throws Exception {

        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.delete("/user/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("deleteUser",
                        pathParameters(
                                parameterWithName("id").description("The id of the input to delete")
                        )))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String entity = response.getResponse().getContentAsString();

        UserDto expectedUserDto = UserDto
                .builder()
                .id(3L)
                .name("Carry")
                .age(13)
                .date(LocalDate.now())
                .build();

        UserDto actualUserDto = objectMapper.readValue(entity, UserDto.class);

        assertEquals(expectedUserDto, actualUserDto);
    }
}
package ru.ithub.nero.service;

import org.apache.catalina.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @ParameterizedTest
    @MethodSource("ru.ithub.nero.argument.UserServiceArg#getCreateUserDtoArgs")
    void createUser_whenValidUser_thenUser(CreateUserDto createUserDto,
                                           UserDto expectedUserDto) {
        UserDto actualUserDto = service.createUser(createUserDto);

        assertEquals(expectedUserDto, actualUserDto);
    }
}
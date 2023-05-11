package ru.ithub.nero.service;

import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.model.exception.ExceptionMessage;
import ru.ithub.nero.model.exception.MyException;
import ru.ithub.nero.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService service;

    private ArrayList<UserDto> storage = new ArrayList<>();

    {
        storage.add(new UserDto(1L, "Sun", 11, LocalDate.now()));
        storage.add(new UserDto(2L, "Moon", 12, LocalDate.now()));
        storage.add(new UserDto(3L, "Earth", 13, LocalDate.now()));
    }

    @ParameterizedTest
    @MethodSource("ru.ithub.nero.argument.UserServiceArg#getCreateUserDtoArgs")
    public void createUser_whenValidUser_thenUser(CreateUserDto createUserDto,
                                           UserDto expectedUserDto) {
        UserDto actualUserDto = service.createUser(createUserDto);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    public void getUsers_whenUsersExists_thenReturnUsers() {
        when(userRepository.getStorage())
                .thenReturn(storage);

        List<UserDto> actualUsers = service.getUsers();

        assertEquals(storage, actualUsers);
    }

    @Test
    public void getUser_whenUserExist_thenReturnUser() {
        Long id = 1L;

        UserDto expectedUser = UserDto.builder()
                .id(id)
                .name("Sun")
                .age(11)
                .date(LocalDate.now())
                .build();

        when(userRepository.findUserById(id))
                .thenReturn(Optional.of(expectedUser));

        Optional<UserDto> actualUser = service.getUser(id);

        assertEquals(Optional.of(expectedUser), actualUser);
    }

    @Test
    public void getUser_whenUserDoesNotExist_thenReturnException() {
        Long id = 4L;

        ExceptionMessage expectedExceptionMessage = ExceptionMessage.NOT_FOUND_WITH_ID;

        when(userRepository.findUserById(id))
                .thenReturn(Optional.empty());

        MyException actualMyException = assertThrows(MyException.class, () -> service.getUser(id));

        assertEquals(expectedExceptionMessage, actualMyException.getExceptionMessage().getExceptionMessage());
    }

    @Test
    public void createUser_whenUserDoesNotExistInStorage_thenReturnUser() {
        String name = "testuser";

        CreateUserDto createUserDto = CreateUserDto
                .builder()
                .name(name)
                .age(50)
                .build();

        UserDto expectedUser = UserDto
                .builder()
                .id(10L)
                .name(createUserDto.getName())
                .age(createUserDto.getAge())
                .date(LocalDate.now())
                .build();

        when(userRepository.create(createUserDto))
                .thenReturn(expectedUser);

        UserDto actualUser = service.createUser(createUserDto);

        assertEquals(expectedUser, actualUser);
    }

//    @Test
//    public void createUser_whenUserExistInStorage_thenReturnException() {
//
//        CreateUserDto createUserDto = CreateUserDto
//                .builder()
//                .name("Sun")
//                .age(11)
//                .build();
//
//        ExceptionMessage expectedExceptionMessage = ExceptionMessage.ALREADY_EXIST_WITH_USER_NAME;
//
//        when(userRepository.create(createUserDto))
//                .thenReturn(null);
//
//        MyException actualMyException = assertThrows(MyException.class, () -> service.createUser(createUserDto));
//
//        assertEquals(expectedExceptionMessage, actualMyException.getExceptionMessage().getExceptionMessage());
//    }

}
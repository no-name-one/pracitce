package ru.ithub.nero.service;

import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ithub.nero.mapper.UserMapper;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.model.entity.User;
import ru.ithub.nero.model.exception.ExceptionMessage;
import ru.ithub.nero.model.exception.MyException;
import ru.ithub.nero.repository.IUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper mapper;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService service;

    private final List<User> storage = new ArrayList<>();

    {
        storage.add(new User(1L, "Tom", 11, LocalDate.now()));
        storage.add(new User(2L, "Jerry", 12, LocalDate.now()));
        storage.add(new User(3L, "postman", 15, LocalDate.now()));
    }

//    @ParameterizedTest
//    @MethodSource("ru.ithub.nero.argument.UserServiceArg#getCreateUserDtoArgs")
//    public void createUser_whenValidUser_thenUser(CreateUserDto createUserDto,
//                                           UserDto expectedUserDto) {
//        UserDto actualUserDto = service.createUser(createUserDto);
//
//        assertEquals(expectedUserDto, actualUserDto);
//    }

//    @Test
//    public void getUsers_whenUsersExists_thenReturnUsers() {
//        when(userRepository.findAll())
//                .thenReturn(storage);
//
//        List<UserDto> actualUsers = service.getUsers();
//
//        assertEquals(storage, mapper.toEntity(actualUsers));
//    }

//    @Test
//    public void getUser_whenUserExist_thenReturnUser() {
//        Long id = 1L;
//
//        UserDto expectedUser = UserDto.builder()
//                .id(id)
//                .name("Tom")
//                .age(11)
//                .date(LocalDate.now())
//                .build();
//
//        when(userRepository.findById(id))
//                .thenReturn(Optional.of(mapper.toEntity(expectedUser)));
//
//        UserDto actualUser = service.getUser(id);
//
//        assertEquals(expectedUser, actualUser);
//    }

    @Test
    public void getUser_whenUserDoesNotExist_thenReturnException() {
        Long id = 100L;

        ExceptionMessage expectedExceptionMessage = ExceptionMessage.NOT_FOUND_WITH_ID;

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        MyException actualMyException = assertThrows(MyException.class, () -> service.getUser(id));

        assertEquals(expectedExceptionMessage, actualMyException.getExceptionMessage());
    }

//    @Test
//    public void createUser_whenUserDoesNotExistInStorage_thenReturnUser() {
//        String name = "testuser";
//
//        CreateUserDto createUserDto = CreateUserDto
//                .builder()
//                .name(name)
//                .age(50)
//                .build();
//
//        UserDto expectedUser = UserDto
//                .builder()
//                .id(10L)
//                .name(createUserDto.getName())
//                .age(createUserDto.getAge())
//                .date(LocalDate.now())
//                .build();
//
//        when(userRepository.save(mapper.toEntity(createUserDto)))
//                .thenReturn(mapper.toEntity(expectedUser));
//
//        UserDto actualUser = service.createUser(createUserDto);
//
//        assertEquals(expectedUser, actualUser);
//    }

//    @Test
//    public void createUser_whenUserExistInStorage_thenReturnException() {
//
//        CreateUserDto createUserDto = CreateUserDto
//                .builder()
//                .name("Tom")
//                .age(11)
//                .build();
//
//        ExceptionMessage expectedExceptionMessage = ExceptionMessage.ALREADY_EXIST_WITH_NAME;
//
//        when(userRepository.save(mapper.toEntity(createUserDto)))
//                .thenReturn(Optional.empty()));
//
//        MyException actualMyException = assertThrows(MyException.class, () -> service.createUser(createUserDto));
//
//        assertEquals(expectedExceptionMessage, actualMyException.getExceptionMessage());
//    }

}
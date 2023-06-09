package ru.ithub.nero.service;

import lombok.ToString;
import org.checkerframework.checker.nullness.Opt;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.model.entity.User;
import ru.ithub.nero.model.exception.ExceptionMessage;
import ru.ithub.nero.model.exception.MyException;
import ru.ithub.nero.repository.IUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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

    @Test
    public void getUsers_whenUsersExists_thenReturnUsers() {
        when(userRepository.findAll())
                .thenReturn(storage);

        List<UserDto> userDtoList = service.getUsers();

        List<User> userList = userDtoList.stream().map(u -> new User(u.getId(), u.getName(), u.getAge(), u.getDate())).collect(Collectors.toList());

        assertEquals(storage, userList);
    }

    @Test
    public void getUser_whenUserExist_thenReturnUser() {
        Long id = 1L;

        UserDto userDto = UserDto.builder()
                .id(id)
                .name("Tom")
                .age(11)
                .date(LocalDate.now())
                .build();

        User expectedUser = User
                .builder()
                .name(userDto.getName())
                .age(userDto.getAge())
                .date(LocalDate.now())
                .build();

        when(userRepository.findById(id))
                .thenReturn(Optional.of(expectedUser));

        UserDto actualUserDto = service.getUser(id);

        User actualUser = User
                .builder()
                .id(actualUserDto.getId())
                .name(actualUserDto.getName())
                .age(actualUserDto.getAge())
                .date(actualUserDto.getDate())
                .build();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void getUser_whenUserDoesNotExist_thenReturnException() {
        Long id = 100L;

        ExceptionMessage expectedExceptionMessage = ExceptionMessage.NOT_FOUND_WITH_ID;

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        MyException actualExceptionMessage = assertThrows(MyException.class, () -> service.getUser(id));

        assertEquals(expectedExceptionMessage, actualExceptionMessage.getExceptionMessage());
    }

    @ParameterizedTest
    @MethodSource("ru.ithub.nero.argument.UserServiceArg#getCreateUserDtoArgs")
    public void createUser_whenValidUser_thenUser(CreateUserDto createUserDto,
                                                  UserDto expectedUserDto) {
        User user = User
                .builder()
                .name(createUserDto.getName())
                .age(createUserDto.getAge())
                .date(LocalDate.now())
                .build();

        User expectedUser = User
                .builder()
                .id(10L)
                .name(user.getName())
                .age(user.getAge())
                .date(user.getDate())
                .build();

        when(userRepository.existsByName(createUserDto.getName()))
                .thenReturn(false);

        when(userRepository.save(user))
                .thenReturn(expectedUser);

        UserDto actualUserDto = service.createUser(createUserDto);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    public void createUser_whenUserDoesNotExistInStorage_thenReturnUser() {
        String name = "testuser";

        CreateUserDto createUserDto = CreateUserDto
                .builder()
                .name(name)
                .age(50)
                .build();

        UserDto userDto = UserDto
                .builder()
                .id(11L)
                .name(name)
                .age(50)
                .date(LocalDate.now())
                .build();

        User user = User
                .builder()
                .name(userDto.getName())
                .age(userDto.getAge())
                .date(userDto.getDate())
                .build();

        User expectedUser = User
                .builder()
                .id(11L)
                .name(user.getName())
                .age(user.getAge())
                .date(user.getDate())
                .build();

        when(userRepository.save(user))
                .thenReturn(expectedUser);

        UserDto actualUserDto = service.createUser(createUserDto);

        User actualUser = User
                .builder()
                .id(actualUserDto.getId())
                .name(actualUserDto.getName())
                .age(actualUserDto.getAge())
                .date(LocalDate.now())
                .build();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void createUser_whenUserExistInStorage_thenReturnException() {

        CreateUserDto createUserDto = CreateUserDto
                .builder()
                .name("Tom")
                .age(11)
                .build();

        ExceptionMessage expectedExceptionMessage = ExceptionMessage.ALREADY_EXIST_WITH_NAME;

        when(userRepository.existsByName(createUserDto.getName()))
                .thenReturn(true);

        MyException actualExceptionMessage = assertThrows(MyException.class, () -> service.createUser(createUserDto));

        assertEquals(expectedExceptionMessage, actualExceptionMessage.getExceptionMessage());
    }

    @Test
    public void createUser_whenUserIsTest_thenReturnTestUser() {
        String name = "test";

        CreateUserDto createUserDto = CreateUserDto
                .builder()
                .name(name)
                .age(40)
                .build();

        UserDto userDto = UserDto
                .builder()
                .id(15L)
                .name(name)
                .age(createUserDto.getAge())
                .date(LocalDate.now())
                .build();

        User user = User
                .builder()
                .name(userDto.getName().concat("user"))
                .age(userDto.getAge())
                .date(userDto.getDate())
                .build();

        User expectedUser = User
                .builder()
                .id(15L)
                .name(user.getName())
                .age(user.getAge())
                .date(user.getDate())
                .build();

        when(userRepository.existsByName(name))
                .thenReturn(false);

        when(userRepository.save(user))
                .thenReturn(expectedUser);

        UserDto actualUserDto = service.createUser(createUserDto);

        User actualUser = User
                .builder()
                .id(actualUserDto.getId())
                .name(actualUserDto.getName())
                .age(actualUserDto.getAge())
                .date(actualUserDto.getDate())
                .build();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void updateUser_whenUserExist_thenReturnUser() {
        Long id = 2L;

        UpdateUserDto updateUserDto = UpdateUserDto
                .builder()
                .name("Jerry")
                .age(12)
                .build();

        when(userRepository.existsById(id))
                .thenReturn(true);

        UserDto userDto = UserDto
                .builder()
                .id(id)
                .name(updateUserDto.getName())
                .age(updateUserDto.getAge())
                .date(LocalDate.now())
                .build();

        User user = User
                .builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .date(userDto.getDate())
                .build();

        User expectedUser = User
                .builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .date(userDto.getDate())
                .build();

        when(userRepository.save(user))
                .thenReturn(expectedUser);

        UserDto actualUserDto = service.updateUser(id, updateUserDto);

        User actualUser = User
                .builder()
                .id(actualUserDto.getId())
                .name(actualUserDto.getName())
                .age(actualUserDto.getAge())
                .date(actualUserDto.getDate())
                .build();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void updateUser_whenUserDoesNotExist_thenReturnException() {
        Long id = 2L;

        UpdateUserDto updateUserDto = UpdateUserDto
                .builder()
                .name("Jerry")
                .age(12)
                .build();

        when(userRepository.existsById(id))
                .thenReturn(false);

        ExceptionMessage expectedExceptionMessage = ExceptionMessage.NOT_FOUND_WITH_ID;

        MyException actualExceptionMessage = assertThrows(MyException.class, () -> service.updateUser(id, updateUserDto));

        assertEquals(expectedExceptionMessage, actualExceptionMessage.getExceptionMessage());
    }

    @Test
    public void updateUser_whenUserIsTest_thenReturnException() {
        Long id = 2L;

        UpdateUserDto updateUserDto = UpdateUserDto
                .builder()
                .name("test")
                .age(20)
                .build();

        when(userRepository.existsById(id))
                .thenReturn(true);

        ExceptionMessage expectedExceptionMessage = ExceptionMessage.USER_IS_TEST;

        MyException actualExceptionMessage = assertThrows(MyException.class, () -> service.updateUser(id, updateUserDto));

        assertEquals(expectedExceptionMessage, actualExceptionMessage.getExceptionMessage());

    }

    @Test
    public void deleteUser_whenUserExist_thenReturnUser() {
        Long id = 30L;

        UserDto expectedUserDto = UserDto
                .builder()
                .id(id)
                .name("some name")
                .age(30)
                .date(LocalDate.now())
                .build();

        Optional<User> user = Optional.of(User
                .builder()
                .id(id)
                .name(expectedUserDto.getName())
                .age(expectedUserDto.getAge())
                .date(expectedUserDto.getDate())
                .build());

        when(userRepository.existsById(id))
                .thenReturn(true);

        when(userRepository.findById(id))
                .thenReturn(user);

        UserDto actualUserDto = service.deleteUser(id);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    public void deleteUser_whenUserDoesNotExist_thenReturnException() {
        Long id = 30L;

        ExceptionMessage expectedExceptionMessage = ExceptionMessage.NOT_FOUND_WITH_ID;

        when(userRepository.existsById(id))
                .thenReturn(false);

        MyException actualExceptionMessage = assertThrows(MyException.class, () -> service.deleteUser(id));

        assertEquals(expectedExceptionMessage, actualExceptionMessage.getExceptionMessage());
    }
}
package ru.ithub.nero.provider;

import org.apache.catalina.User;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;

import java.time.LocalDate;
import java.util.UUID;

public class UserProvider {

    public static CreateUserDto getCreateUserDto(String name) {
        return CreateUserDto.builder()
                .name(name)
                .age(10)
                .date(LocalDate.now())
                .build();
    }

    public static UserDto getUserDto(String name) {
        return UserDto.builder()
                .id(1L)
                .name(name)
                .age(10)
                .date(LocalDate.now())
                .build();
    }
}

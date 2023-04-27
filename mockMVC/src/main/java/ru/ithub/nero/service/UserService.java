package ru.ithub.nero.service;

import org.springframework.stereotype.Service;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;

@Service
public class UserService implements IUserService {
    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        UserDto userDto;

        if (createUserDto.getName().equals("test")) {
            userDto = UserDto.builder()
                    .id(1L)
                    .name(createUserDto.getName().concat("user"))
                    .age(createUserDto.getAge())
                    .date(createUserDto.getDate())
                    .build();
        } else {
            userDto = UserDto.builder()
                    .id(1L)
                    .name(createUserDto.getName())
                    .age(createUserDto.getAge())
                    .date(createUserDto.getDate())
                    .build();
        }

        return userDto;
    }
}

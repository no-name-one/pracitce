package ru.ithub.nero.service;

import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;

import java.util.ArrayList;
import java.util.Optional;

public interface IUserService {
    ArrayList<UserDto> getUsers();
    UserDto getUser(Long id);
    UserDto createUser(CreateUserDto createUserDto);

    UserDto updateUser(Long id, UpdateUserDto updateUserDto);

    UserDto deleteUser(Long id);
}

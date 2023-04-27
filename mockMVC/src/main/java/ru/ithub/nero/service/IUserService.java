package ru.ithub.nero.service;

import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;

public interface IUserService {
    UserDto createUser(CreateUserDto createUserDto);
}

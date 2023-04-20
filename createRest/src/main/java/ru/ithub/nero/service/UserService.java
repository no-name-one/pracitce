package ru.ithub.nero.service;

import ru.ithub.nero.model.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> findAll();
    UserDto getByID(UUID id);
    UserDto create(UserDto userDTO);
    UserDto update(UserDto userDTO);
    void deleteById(UUID id);
}

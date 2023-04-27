package ru.ithub.nero.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> findAll();
    UserDto getByID(UUID id);

    CreateUserDto create(CreateUserDto createUserDto);

    UserDto update(UserDto userDTO);
    void deleteById(UUID id);
}

package ru.ithub.nero.service;

import ru.ithub.nero.model.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO getByID(UUID id);
    UserDTO create(UserDTO user);
    UserDTO update(UserDTO user);
    void deleteById(UUID id);
}

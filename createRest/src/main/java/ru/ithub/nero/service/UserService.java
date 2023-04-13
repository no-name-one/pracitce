package ru.ithub.nero.service;

import ru.ithub.nero.model.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO getByID(UUID id);
    UserDTO create(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    void deleteById(UUID id);
}

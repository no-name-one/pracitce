package ru.ithub.nero.service;

import ru.ithub.nero.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();
    User getByID(UUID id);

    User getById(UUID id);

    User create(User user);
    User update(User user);
    void deleteById(UUID id);
}

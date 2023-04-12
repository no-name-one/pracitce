package ru.ithub.nero.service.Impl;

import org.springframework.stereotype.Service;
import ru.ithub.nero.entity.User;
import ru.ithub.nero.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private List<User> storage = new ArrayList<>();
    {
        storage.add(new User(UUID.randomUUID(), "Ball", 12));
        storage.add(new User(UUID.randomUUID(), "Green", 14));
        storage.add(new User(UUID.randomUUID(), "Carry", 20));
        storage.add(new User(UUID.randomUUID(), "Moon", 10));
    }


    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public User getByID(UUID id) {
        return null;
    }

    private boolean existById(UUID id) {
        for (User user : storage) {
            if (user.getUuid().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getById(UUID id) {return null;}

    @Override
    public User create(User user) {return null;}

    @Override
    public User update(User user) {return null;}

    @Override
    public void deleteById(UUID id) {};
}

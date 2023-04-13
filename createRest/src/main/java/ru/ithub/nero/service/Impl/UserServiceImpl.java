package ru.ithub.nero.service.Impl;

import org.springframework.stereotype.Service;
import ru.ithub.nero.model.UserDTO;
import ru.ithub.nero.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private List<UserDTO> storage = new ArrayList<>();
    {
        storage.add(new UserDTO(UUID.randomUUID(), "Ball", 12));
        storage.add(new UserDTO(UUID.randomUUID(), "Green", 14));
        storage.add(new UserDTO(UUID.randomUUID(), "Carry", 20));
        storage.add(new UserDTO(UUID.randomUUID(), "Moon", 10));
    }

    private interface Messages {
        String ALREADY_EXIST = "User already exist with username: %s";
        String NOT_FOUND_WITH_USERNAME = "User doesn't exist with username: %s";
        String NOT_FOUND_WITH_ID = "User not found with id: %s";
    }

    @Override
    public List<UserDTO> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public UserDTO getByID(UUID id) {
//        List<User> collect = new ArrayList<>();
        for (UserDTO user : storage) {
            if (user.getUuid().equals(id)) {
                return user;
            }
        }

        throw new RuntimeException(String.format(Messages.NOT_FOUND_WITH_ID, id));
    }

    private boolean existById(UUID id) {
        for (UserDTO user : storage) {
            if (user.getUuid().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean existByUserName(String username) {
        for (UserDTO user : storage) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public UserDTO create(UserDTO user) {
        for (UserDTO existing : storage) {
            if (existing.getUserName().equals(user.getUserName())) {
                throw new RuntimeException(String.format(Messages.ALREADY_EXIST, user.getUserName()));
            }
        }

        user.setUuid(UUID.randomUUID());
        storage.add(user);
        return user;
    }

    @Override
    public UserDTO update(UserDTO user) {
        for (UserDTO existing : storage) {
            if (existById(user.getUuid())) {

                if (!existByUserName(user.getUserName())) {
                    existing.setUserName(user.getUserName());
                } else {
                    throw new RuntimeException(String.format(Messages.ALREADY_EXIST, user.getUserName()));
                }

                if (user.getAge() > 0) {
                    existing.setAge(user.getAge());
                }

                break;
            }

            throw new RuntimeException(String.format(Messages.NOT_FOUND_WITH_ID, user.getUuid()));
        }
        return user;
    }

    @Override
    public void deleteById(UUID id) {
        boolean isDeleted = false;
        for (UserDTO user : storage) {
            if (user.getUuid().equals(id)) {
                storage.remove(user);
                isDeleted = true;
                break;
            }
        }
        if (!isDeleted) {
            throw new RuntimeException(String.format(Messages.NOT_FOUND_WITH_ID, id));
        }
    };
}

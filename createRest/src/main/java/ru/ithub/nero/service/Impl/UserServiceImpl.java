package ru.ithub.nero.service.Impl;

import org.springframework.stereotype.Service;
import ru.ithub.nero.model.UserDTO;
import ru.ithub.nero.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final List<UserDTO> storage = new ArrayList<>();
    {
        storage.add(new UserDTO(UUID.randomUUID(), "Ball", 12));
        storage.add(new UserDTO(UUID.randomUUID(), "Green", 14));
        storage.add(new UserDTO(UUID.randomUUID(), "Carry", 20));
        storage.add(new UserDTO(UUID.randomUUID(), "Moon", 10));
    }

    private interface Messages {
        String ALREADY_EXIST_WITH_USERNAME= "User already exist with username: %s";
        String NOT_FOUND_WITH_USERNAME = "User doesn't exist with username: %s";
        String NOT_FOUND_WITH_ID = "User not found with id: %s";
    }

    @Override
    public List<UserDTO> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public UserDTO getByID(UUID id) {
        for (UserDTO userDTO : storage) {
            if(userDTO.getUuid().equals(id)) {
                return userDTO;
            }
        }
        throw new RuntimeException(String.format(Messages.NOT_FOUND_WITH_ID, id));
    }

    private boolean existById(UUID id) {
        for (UserDTO userDTO : storage) {
            if (userDTO.getUuid().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean existByUserName(String username) {
        for (UserDTO userDTO : storage) {
            if (userDTO.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public UserDTO create(UserDTO userDTO) {
        if (existByUserName(userDTO.getUsername())) {
            throw new RuntimeException(String.format(Messages.ALREADY_EXIST_WITH_USERNAME, userDTO.getUsername()));
        }

        userDTO.setUuid(UUID.randomUUID());
        storage.add(userDTO);

        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        for (UserDTO existing : storage) {
            if (existing.getUuid().equals(userDTO.getUuid())) {
                if (!existByUserName(userDTO.getUsername())) {
                    existing.setUsername(userDTO.getUsername());
                } else {
                    throw new RuntimeException(String.format(Messages.ALREADY_EXIST_WITH_USERNAME, userDTO.getUsername()));
                }

                if (userDTO.getAge() > 0) {
                    existing.setAge(userDTO.getAge());
                }

                break;
            }

            throw new RuntimeException(String.format(Messages.NOT_FOUND_WITH_ID, userDTO.getUuid()));
        }

        return userDTO;
    }

    @Override
    public void deleteById(UUID id) {
        boolean isDeleted = false;
        for (UserDTO userDTO : storage) {
            if (userDTO.getUuid().equals(id)) {
                storage.remove(userDTO);
                isDeleted = true;
                break;
            }
        }
        if (!isDeleted) {
            throw new RuntimeException(String.format(Messages.NOT_FOUND_WITH_ID, id));
        }
    };
}

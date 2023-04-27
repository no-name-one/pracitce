package ru.ithub.nero.service.Impl;

import org.springframework.stereotype.Service;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.exception.CustomException;
import ru.ithub.nero.model.exception.ExceptionMessage;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final List<UserDto> storage = new ArrayList<>();
    {
        storage.add(new UserDto(UUID.randomUUID(), "Ball", 12));
        storage.add(new UserDto(UUID.randomUUID(), "Green", 14));
        storage.add(new UserDto(UUID.randomUUID(), "Carry", 20));
        storage.add(new UserDto(UUID.randomUUID(), "Moon", 10));
    }

    @Override
    public List<UserDto> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public UserDto getByID(UUID id) {
        for (UserDto userDTO : storage) {
            if(userDTO.getUuid().equals(id)) {
                return userDTO;
            }
        }
        throw new CustomException(ExceptionMessage.NOT_FOUND_WITH_ID);
    }

    private boolean existById(UUID id) {
        for (UserDto userDTO : storage) {
            if (userDTO.getUuid().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean existByName(String name) {
        for (UserDto userDTO : storage) {
            if (userDTO.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CreateUserDto create(CreateUserDto createUserDto) {
        if (existByName(createUserDto.getName())) {
            throw new CustomException(ExceptionMessage.ALREADY_EXIST_WITH_USERNAME);
        }

        UserDto userDto = new UserDto(UUID.randomUUID(), createUserDto.getName(), createUserDto.getAge());
        storage.add(userDto);

        return createUserDto;
    }

    @Override
    public UserDto update(UserDto userDTO) {
        for (UserDto existing : storage) {
            if (existing.getUuid().equals(userDTO.getUuid())) {
                if (!existByName(userDTO.getName())) {
                    existing.setName(userDTO.getName());
                } else {
                    throw new CustomException(ExceptionMessage.ALREADY_EXIST_WITH_USERNAME);
                }

                if (userDTO.getAge() > 0) {
                    existing.setAge(userDTO.getAge());
                }

                break;
            }

            throw new CustomException(ExceptionMessage.NOT_FOUND_WITH_ID);
        }

        return userDTO;
    }

    @Override
    public void deleteById(UUID id) {
        boolean isDeleted = false;
        for (UserDto userDTO : storage) {
            if (userDTO.getUuid().equals(id)) {
                storage.remove(userDTO);
                isDeleted = true;
                break;
            }
        }
        if (!isDeleted) {
            throw new CustomException(ExceptionMessage.NOT_FOUND_WITH_ID);
        }
    };
}

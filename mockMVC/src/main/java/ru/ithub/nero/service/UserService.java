package ru.ithub.nero.service;

import org.springframework.stereotype.Service;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<UserDto> getUsers() {
        return userRepository.getStorage();
    }

    @Override
    public Optional<UserDto> getUser(Long id) {
        Optional<UserDto> userDto = userRepository.findUserById(id);
        return userDto;
    }


    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        UserDto userDto;

        if (createUserDto.getName().equals("test")) {
            userDto = UserDto.builder()
                    .id(10L)
                    .name(createUserDto.getName().concat("user"))
                    .age(createUserDto.getAge())
                    .date(LocalDate.now())
                    .build();
        } else {
            userDto = UserDto.builder()
                    .id(10L)
                    .name(createUserDto.getName())
                    .age(createUserDto.getAge())
                    .date(LocalDate.now())
                    .build();
        }

        userRepository.save(userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        UserDto userDto;
        if (userRepository.existById(id)) {

            if (!updateUserDto.getName().startsWith("test")) {
                userDto = UserDto.builder()
                        .id(id)
                        .name(updateUserDto.getName())
                        .age(updateUserDto.getAge())
                        .date(LocalDate.now())
                        .build();
                userRepository.update(id, updateUserDto);
            } else {
                throw new RuntimeException("user is test");
            }
        } else {
            throw new RuntimeException("Not found user with id: " + id);
        }
        return userDto;
    }

    @Override
    public UserDto deleteUser(Long id) {
        UserDto userDto;
        userDto = userRepository.delete(id);

        return userDto;
    }


}

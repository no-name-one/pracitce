package ru.ithub.nero.service;

import org.springframework.stereotype.Service;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.model.exception.ExceptionMessage;
import ru.ithub.nero.model.exception.MyException;
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
        UserDto userDto = userRepository.create(createUserDto);
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
                throw new MyException(ExceptionMessage.USER_IS_TEST);
            }
        } else {
            throw new MyException(ExceptionMessage.NOT_FOUND_WITH_ID);
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

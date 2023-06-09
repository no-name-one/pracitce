package ru.ithub.nero.service;

import org.springframework.stereotype.Service;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.model.entity.User;
import ru.ithub.nero.model.exception.ExceptionMessage;
import ru.ithub.nero.model.exception.MyException;
import ru.ithub.nero.repository.IUserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream().map(u -> new UserDto(u.getId(), u.getName(), u.getAge(), u.getDate())).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new MyException(ExceptionMessage.NOT_FOUND_WITH_ID));
        UserDto userDto = UserDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .date(LocalDate.now())
                .build();
        return userDto;
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        if (!userRepository.existsByName(createUserDto.getName())) {
            UserDto userDto;

            if (createUserDto.getName().equals("test")) {
                userDto = UserDto
                        .builder()
                        .name(createUserDto.getName().concat("user"))
                        .age(createUserDto.getAge())
                        .date(LocalDate.now())
                        .build();
            } else {
                userDto = UserDto
                        .builder()
                        .name(createUserDto.getName())
                        .age(createUserDto.getAge())
                        .date(LocalDate.now())
                        .build();
            }

            User user = User
                    .builder()
                    .name(userDto.getName())
                    .age(userDto.getAge())
                    .date(userDto.getDate())
                    .build();

            User actualUser = userRepository.save(user);

            UserDto actualUserDto = UserDto
                    .builder()
                    .id(actualUser.getId())
                    .name(actualUser.getName())
                    .age(actualUser.getAge())
                    .date(actualUser.getDate())
                    .build();

            return actualUserDto;
        }
        throw new MyException(ExceptionMessage.ALREADY_EXIST_WITH_NAME);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        if (userRepository.existsById(id)) {

            if (!updateUserDto.getName().startsWith("test")) {

                UserDto userDto = UserDto.builder()
                        .id(id)
                        .name(updateUserDto.getName())
                        .age(updateUserDto.getAge())
                        .date(LocalDate.now())
                        .build();

                User user = User
                        .builder()
                        .id(userDto.getId())
                        .name(userDto.getName())
                        .age(userDto.getAge())
                        .date(userDto.getDate())
                        .build();

                userRepository.save(user);
                return userDto;
            }
            throw new MyException(ExceptionMessage.USER_IS_TEST);
        }
        throw new MyException(ExceptionMessage.NOT_FOUND_WITH_ID);
    }

    @Override
    public UserDto deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            Optional<User> user = userRepository.findById(id);

            UserDto userDto = UserDto
                    .builder()
                    .id(user.get().getId())
                    .name(user.get().getName())
                    .age(user.get().getAge())
                    .date(user.get().getDate())
                    .build();

            userRepository.deleteById(id);
            return userDto;
        }
        throw new MyException(ExceptionMessage.NOT_FOUND_WITH_ID);
    }
}

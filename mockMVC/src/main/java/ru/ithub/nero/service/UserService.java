package ru.ithub.nero.service;

import org.springframework.stereotype.Service;
import ru.ithub.nero.mapper.UserMapper;
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

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    private final UserMapper mapper;

    public UserService(IUserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return mapper.toUserDto(users);
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new MyException(ExceptionMessage.NOT_FOUND_WITH_ID));
        return mapper.toUserDto(user);
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        if (!existByName(createUserDto.getName())) {
            User mapperEntity = mapper.toEntity(createUserDto);
            Optional<User> user = Optional.of(userRepository.save(mapperEntity));
            return mapper.toUserDto(user.orElseThrow(() -> new MyException(ExceptionMessage.ALREADY_EXIST_WITH_NAME)));
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
                userRepository.save(mapper.toEntity(userDto));
                return userDto;
            }
            throw new MyException(ExceptionMessage.USER_IS_TEST);
        }
        throw new MyException(ExceptionMessage.NOT_FOUND_WITH_ID);
    }

    @Override
    public UserDto deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            UserDto userDto =  mapper.toUserDto(userRepository.findById(id));
            userRepository.deleteById(id);
            return userDto;
        }
        throw new MyException(ExceptionMessage.NOT_FOUND_WITH_ID);
    }

    public boolean existByName(String name) {
        for (User user : userRepository.findAll()) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

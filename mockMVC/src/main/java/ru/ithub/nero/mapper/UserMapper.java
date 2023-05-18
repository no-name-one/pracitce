package ru.ithub.nero.mapper;

import org.springframework.stereotype.Component;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.model.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getAge(),
                userDto.getDate()
        );
    }

    public User toEntity(CreateUserDto createUserDto) {
        return new User(
                null,
                createUserDto.getName(),
                createUserDto.getAge(),
                LocalDate.now()
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getDate()
        );
    }

    public UserDto toUserDto(Optional<User> userOptional) {
        return new UserDto(
                userOptional.get().getId(),
                userOptional.get().getName(),
                userOptional.get().getAge(),
                userOptional.get().getDate()
        );
    }

    public List<User> toEntity(List<UserDto> userDtoList) {
        return userDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<UserDto> toUserDto(List<User> userList) {
        return userList.stream().map(this::toUserDto).collect(Collectors.toList());
    }
}
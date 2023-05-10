package ru.ithub.nero.repository;

import org.springframework.stereotype.Repository;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;

import java.util.ArrayList;
import java.util.Optional;


@Repository
public interface IUserRepository {
    Optional<UserDto> findUserById(Long id);

    boolean existByName(String name);

    boolean existById(Long id);

    ArrayList<UserDto> getStorage();

    void save(UserDto userDto);

    UserDto update(Long id, UpdateUserDto updateUserDto);

    UserDto delete(Long id);
}

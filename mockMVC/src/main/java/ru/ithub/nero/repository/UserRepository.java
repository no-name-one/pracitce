package ru.ithub.nero.repository;

import org.springframework.stereotype.Repository;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.model.exception.ExceptionMessage;
import ru.ithub.nero.model.exception.MyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository{
    private static ArrayList<UserDto> storage = new ArrayList<>();
    {
        storage.add(new UserDto(1L, "Sun", 11, LocalDate.now()));
        storage.add(new UserDto(2L, "Moon", 12, LocalDate.now()));
        storage.add(new UserDto(3L, "Earth", 13, LocalDate.now()));
    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
        for (UserDto userDto : storage) {
            if (userDto.getId().equals(id)) {
                Optional<UserDto> optionalUserDto = Optional.of(userDto);
                return optionalUserDto;
            }
        }
        throw new MyException(ExceptionMessage.NOT_FOUND_WITH_ID);
    }

    @Override
    public boolean existByName(String name) {
        for (UserDto userDto : storage) {
            if (userDto.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        for (UserDto userDto : storage) {
            if (userDto.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<UserDto> getStorage() {
        return new ArrayList<>(storage);
    }

    @Override
    public void save(UserDto userDto) {
        storage.add(userDto);
    }

    @Override
    public UserDto create(CreateUserDto createUserDto) {
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

        save(userDto);

        return userDto;
    }

    @Override
    public UserDto update(Long id, UpdateUserDto updateUserDto) {
        UserDto updatedUser = new UserDto();
        for (UserDto userDto : storage) {
            if (userDto.getId().equals(id)) {
                if (!existByName(updateUserDto.getName())) {
                    userDto.setName(updateUserDto.getName());
                } else {
                    throw new MyException(ExceptionMessage.ALREADY_EXIST_WITH_USER_NAME);
                }

                userDto.setAge(updateUserDto.getAge());

                updatedUser = UserDto.builder()
                        .id(id)
                        .name(userDto.getName())
                        .age(userDto.getAge())
                        .date(userDto.getDate())
                        .build();

                break;
            } else if (!userDto.getId().equals(id)) {
                continue;
            }

            throw new MyException(ExceptionMessage.NOT_FOUND_WITH_ID);
        }

        return updatedUser;
    }

    @Override
    public UserDto delete(Long id) {
        UserDto deletedUserDto = new UserDto();
        for (UserDto userDto : storage) {
            if (userDto.getId().equals(id)) {
                deletedUserDto = UserDto.builder()
                        .id(userDto.getId())
                        .name(userDto.getName())
                        .age(userDto.getAge())
                        .date(userDto.getDate())
                        .build();
                storage.remove(userDto);
                break;
            } else if (!userDto.getId().equals(id)){
                continue;
            }
            throw new MyException(ExceptionMessage.NOT_FOUND_WITH_ID);
        }
        return deletedUserDto;
    }
}
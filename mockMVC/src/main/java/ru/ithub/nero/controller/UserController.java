package ru.ithub.nero.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.repository.IUserRepository;
import ru.ithub.nero.service.IUserService;

import java.util.ArrayList;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping
    public ArrayList<UserDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<UserDto> getUser(@PathVariable Long id) {
        Optional<UserDto> userDto = service.getUser(id);
        return userDto;
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("Get request for create user: {}", createUserDto);

        UserDto userDto = service.createUser(createUserDto);

        log.info("Return response for create user: {}", userDto);

        return userDto;
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id,
                              @Valid @RequestBody UpdateUserDto updateUserDto) {
        log.info("Get request for update user: {}", updateUserDto);

        UserDto userDto = service.updateUser(id, updateUserDto);

        log.info("Return response for update user: {}", userDto);

        return userDto;
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Long id) {

        UserDto userDto = service.deleteUser(id);

        return userDto;
    }
}
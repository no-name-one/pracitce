package ru.ithub.nero.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.service.IUserService;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("Get request for create user: {}", createUserDto);

        UserDto userDto = service.createUser(createUserDto);

        log.info("Return response for create user: {}", userDto);

        return userDto;
    }
}

package ru.ithub.nero.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.ithub.nero.model.dto.CreateUserDto;
import ru.ithub.nero.model.dto.UpdateUserDto;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.service.IUserService;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    RabbitTemplate template;

    private final IUserService service;

    public UserController(@Qualifier("userService") IUserService service, RabbitTemplate template) {
        this.service = service;
        this.template = template;
    }

//    @GetMapping("/emit/error")
//    @ResponseBody
//    public String error() {
//        logger.info("Emit as error");
//        template.convertAndSend("error", "Error");
//        return "Emit as error";
//    }
//
//    @GetMapping("/emit/info")
//    @ResponseBody
//    public String info() {
//        logger.info("Emit as info");
//        template.convertAndSend("info", "Info");
//        return "Emit as info";
//    }
//
//    @GetMapping("/emit/warning")
//    @ResponseBody
//    public String warning() {
//        logger.info("Emit as warning");
//        template.convertAndSend("warning", "Warning");
//        return "Emit as warning";
//    }

    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Get request for get users");

        List<UserDto> users = service.getUsers();

        log.info("Get response for get users: {}", users);

        return users;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        log.info("Get request for get user by id: {}", id);

        UserDto userDto = service.getUser(id);

        log.info("Get response for get user by id: {}", userDto);
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

        log.info("Get request for delete user by id: {}", id);

        UserDto userDto = service.deleteUser(id);

        log.info("Return response for delete user by id: {}", userDto);

        return userDto;
    }
}

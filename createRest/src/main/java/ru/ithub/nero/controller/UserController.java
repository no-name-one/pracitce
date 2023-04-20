package ru.ithub.nero.controller;

import org.springframework.web.bind.annotation.*;
import ru.ithub.nero.model.dto.UserDto;
import ru.ithub.nero.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto getByID(@PathVariable UUID id) {
        return userService.getByID(id);
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userDTO) {
        return userService.update(userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
    }
}

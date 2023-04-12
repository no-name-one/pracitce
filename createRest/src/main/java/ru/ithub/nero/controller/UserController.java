package ru.ithub.nero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ithub.nero.entity.User;
import ru.ithub.nero.service.Impl.UserServiceImpl;
import ru.ithub.nero.service.UserService;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<User> findAll() {return userService.findAll();}
}

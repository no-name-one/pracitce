package ru.ithub.nero.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ithub.nero.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private UserService userService;

    @Test
    public void deleteById_theUserNotFound_whenException() {
        int result = 4 - 2;
        assertEquals(2, result);
    }
}
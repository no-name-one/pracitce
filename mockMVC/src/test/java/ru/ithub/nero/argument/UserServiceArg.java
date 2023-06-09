package ru.ithub.nero.argument;

import org.junit.jupiter.params.provider.Arguments;
import ru.ithub.nero.provider.UserProvider;

import java.util.stream.Stream;

public class UserServiceArg {
    public static Stream<Arguments> getCreateUserDtoArgs() {
        return Stream.of(
                Arguments.of(
                        UserProvider.getCreateUserDto("testuser"),
                        UserProvider.getUserDto("testuser")
                ),
                Arguments.of(
                        UserProvider.getCreateUserDto("user"),
                        UserProvider.getUserDto("user")
                )
        );
    }
}

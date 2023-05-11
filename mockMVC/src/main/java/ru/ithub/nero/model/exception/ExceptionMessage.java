package ru.ithub.nero.model.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ExceptionMessage {
    ALREADY_EXIST_WITH_USER_NAME(HttpStatus.BAD_REQUEST, "User already exist"),
    NOT_FOUND_WITH_USERNAME(HttpStatus.NOT_FOUND, "User not found with username"),
    NOT_FOUND_WITH_ID(HttpStatus.NOT_FOUND, "User not found with id"),
    USER_IS_TEST(HttpStatus.BAD_REQUEST, "User is test");

    private HttpStatus httpStatus;
    private String exceptionMessage;

    ExceptionMessage(HttpStatus httpStatus, String exceptionMessage) {
        this.httpStatus = httpStatus;
        this.exceptionMessage = exceptionMessage;
    }
}

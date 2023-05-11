package ru.ithub.nero.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MyException extends RuntimeException {
    private final ExceptionMessage exceptionMessage;
}

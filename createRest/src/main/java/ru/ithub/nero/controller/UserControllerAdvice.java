package ru.ithub.nero.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ithub.nero.model.exception.CustomException;
import ru.ithub.nero.model.dto.ExceptionDto;

@Slf4j
@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDto> handleCustomException(CustomException customException) {
        log.error("custom error: {}", customException);
        ExceptionDto exceptionDto = ExceptionDto
                .builder()
                .exceptionMessage(customException.getExceptionMessage())
                .message(customException.getExceptionMessage().getExceptionMessage())
                .build();
        log.error("return response: ExceptionDto={}", exceptionDto);
        return ResponseEntity.status(customException.getExceptionMessage().getHttpStatus()).body(exceptionDto);
    }
}

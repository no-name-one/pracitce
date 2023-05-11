package ru.ithub.nero.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ithub.nero.model.dto.ExceptionDto;
import ru.ithub.nero.model.exception.MyException;

@Slf4j
@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(MyException.class)
    public ResponseEntity<ExceptionDto> handleMyException(MyException myException) {
        log.error("my exception error: {}", myException);

        ExceptionDto exceptionDto = ExceptionDto
                .builder()
                .exceptionMessage(myException.getExceptionMessage())
                .message(myException.getExceptionMessage().getExceptionMessage())
                .build();

        log.error("return response: ExceptionDto={}", exceptionDto);

        return ResponseEntity.status(myException.getExceptionMessage().getHttpStatus()).body(exceptionDto);
    }
}

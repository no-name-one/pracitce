package ru.ithub.nero.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 0, message = "Age should be grater than 0")
    @Max(value = 200, message = "Age should be between 0 and 200")
    private Integer age;

    @FutureOrPresent(message = "Date should be present or future")
    @NotEmpty(message = "Date should not be empty")
    private Date date;
}

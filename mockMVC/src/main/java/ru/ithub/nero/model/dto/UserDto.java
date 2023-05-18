package ru.ithub.nero.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @NotEmpty
    @ToString.Exclude
    private String name;

    @Min(value = 0, message = "Age should be grater than 0")
    @Max(value = 200, message = "Age should be between 0 and 200")
    private Integer age;

    @FutureOrPresent
    private LocalDate date;
}

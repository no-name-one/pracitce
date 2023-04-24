package ru.ithub.nero.model.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID uuid;
    private String name;
    private Integer age;
}

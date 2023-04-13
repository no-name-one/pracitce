package ru.ithub.nero.model;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID uuid;
    private String userName;
    private int age;
}
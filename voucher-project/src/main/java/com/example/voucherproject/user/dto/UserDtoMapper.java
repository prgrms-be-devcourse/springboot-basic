package com.example.voucherproject.user.dto;

import com.example.voucherproject.user.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class UserDtoMapper {

    public static User asUserModel(UserDTO.Create userDTO) {
        return User.builder()
                .id(UUID.randomUUID())
                .name(userDTO.getName())
                .type(userDTO.getType())
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .updatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .build();
    }

    public static UserDTO.Result asUserDTO(User user) {
        return UserDTO.Result.builder()
                .id(user.getId())
                .type(user.getType())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

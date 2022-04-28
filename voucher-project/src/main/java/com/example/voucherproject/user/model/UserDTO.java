package com.example.voucherproject.user.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class UserDTO {

    private final UUID id;

    @NotEmpty
    private final String name;

    @NotEmpty
    private final UserType type;

    public static User asUserModel(UserDTO userDTO) {
        // TODO: validation
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .type(userDTO.getType())
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .updatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .build();
    }

    public static UserDTO asUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .type(user.getType())
                .build();
    }
}

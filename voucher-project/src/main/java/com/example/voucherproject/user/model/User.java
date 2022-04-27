package com.example.voucherproject.user.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private final UUID id;
    private final UserType type;
    private final String name;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

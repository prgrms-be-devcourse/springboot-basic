package com.example.voucherproject.user.model;

import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private final UUID id;
    private UserType type;
    private String name;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

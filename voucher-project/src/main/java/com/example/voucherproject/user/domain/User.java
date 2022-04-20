package com.example.voucherproject.user.domain;

import com.example.voucherproject.user.enums.UserType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class User {
    private final UUID id;
    private final UserType type;
    private final String name;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.example.voucherproject.user.model;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserFactory {
    public static User createUser(String userName, UserType userType) {
        return new User(UUID.randomUUID(), userType, userName,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }

}

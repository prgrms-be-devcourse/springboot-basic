package com.example.voucherproject.user.domain;

import com.example.voucherproject.user.enums.UserType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class UserFactory {
    public static User create(String userName, UserType userType) {
        return new User(UUID.randomUUID(),userType, userName, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }
}

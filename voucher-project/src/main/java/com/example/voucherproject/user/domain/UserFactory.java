package com.example.voucherproject.user.domain;

import com.example.voucherproject.common.enums.UserType;

import java.util.UUID;

public class UserFactory {
    public static User create(String userName, UserType userType) {
        return new User(UUID.randomUUID(),userType, userName);
    }

}

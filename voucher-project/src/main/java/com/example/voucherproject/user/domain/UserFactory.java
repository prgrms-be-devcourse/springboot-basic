package com.example.voucherproject.user.domain;
import com.example.voucherproject.common.enums.UserType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFactory {

    public User create(String userName, UserType userType) {
        return new User(UUID.randomUUID(),userType, userName);
    }

}

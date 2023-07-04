package com.dev.bootbasic.user.controller.dto;

import com.dev.bootbasic.user.domain.User;

import java.util.UUID;

public record UserCreateResponse(UUID id, String name) {

    public static UserCreateResponse from(User savedUser) {
        return new UserCreateResponse(savedUser.getId(), savedUser.getName());
    }
}

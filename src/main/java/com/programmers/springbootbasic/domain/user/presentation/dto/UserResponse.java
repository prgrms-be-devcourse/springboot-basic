package com.programmers.springbootbasic.domain.user.presentation.dto;

import com.programmers.springbootbasic.domain.user.domain.entity.User;

public class UserResponse {
    private final String name;

    public UserResponse(String name) {
        this.name = name;
    }

    public static UserResponse of(User user) {
        return new UserResponse(user.getName());
    }

    @Override
    public String toString() {
        return """
            name = %s
            ===============================
            """.formatted(name);
    }
}

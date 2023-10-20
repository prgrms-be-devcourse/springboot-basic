package com.programmers.springbootbasic.domain.user.presentation.dto;

import com.programmers.springbootbasic.domain.user.domain.entity.User;

public class UserResponse {
    private String name;
    @Override
    public String toString() {
        return
            "name =" + name + System.lineSeparator() + "===============================" + System.lineSeparator();
    }

    public UserResponse(String name) {
        this.name = name;
    }

    public static UserResponse of(User user) {
        return new UserResponse(user.getName());
    }
}

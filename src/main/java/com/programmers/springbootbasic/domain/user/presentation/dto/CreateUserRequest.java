package com.programmers.springbootbasic.domain.user.presentation.dto;

import com.programmers.springbootbasic.domain.user.domain.entity.User;

public class CreateUserRequest {

    private String nickname;

    public CreateUserRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public static CreateUserRequest of(String nickname) {
        return new CreateUserRequest(nickname);
    }

    public User toEntity() {
        return new User(null, nickname, false);
    }

}

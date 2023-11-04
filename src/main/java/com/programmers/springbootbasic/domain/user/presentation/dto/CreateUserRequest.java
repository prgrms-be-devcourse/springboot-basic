package com.programmers.springbootbasic.domain.user.presentation.dto;

import com.programmers.springbootbasic.domain.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


public class CreateUserRequest {
    @NotNull
    @Length(min = 1, max = 10)
    private final String nickname;

    private CreateUserRequest(String nickname) {
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

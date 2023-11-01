package com.programmers.springbootbasic.domain.user.presentation.dto;

import com.programmers.springbootbasic.domain.user.domain.entity.User;
import java.util.Objects;

public class UserResponse {

    private final String nickname;

    public UserResponse(String nickname) {
        this.nickname = nickname;
    }

    public static UserResponse of(User user) {
        return new UserResponse(user.getNickname());
    }

    @Override
    public String toString() {
        return """
            name = %s
            ===============================
            """.formatted(nickname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserResponse that)) {
            return false;
        }
        return Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    public String getNickname() {
        return nickname;
    }
}

package com.programmers.springbootbasic.domain.user.domain.entity;

import java.util.Objects;

public class User {

    private final Long id;
    private final String nickname;
    private final boolean blocked;

    public User(Long id, String nickname, boolean blocked) {
        this.id = id;
        this.nickname = nickname;
        this.blocked = blocked;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isBlocked() {
        return blocked;
    }

    // distinct()를 위해 equals와 hashCode를 재정의 해야 한다.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        return isBlocked() == user.isBlocked() && Objects.equals(getId(), user.getId())
            && Objects.equals(getNickname(), user.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNickname(), isBlocked());
    }
}

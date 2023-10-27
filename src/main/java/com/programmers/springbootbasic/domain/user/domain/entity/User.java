package com.programmers.springbootbasic.domain.user.domain.entity;

public class User {

    private final Long id;
    private final String nickname;

    public User(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

}

package com.programmers.springbootbasic.domain.user.infrastructure.dto;

import com.programmers.springbootbasic.domain.user.domain.entity.User;

public class CsvUser {

    private String id;
    private String name;

    public CsvUser() {
    }

    public CsvUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CsvUser of(User user) {
        return new CsvUser(user.getId().toString(), user.getNickname());
    }

    public User toEntity() {
        return new User(Long.valueOf(id), name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

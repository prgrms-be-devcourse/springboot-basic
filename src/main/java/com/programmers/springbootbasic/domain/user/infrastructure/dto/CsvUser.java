package com.programmers.springbootbasic.domain.user.infrastructure.dto;

import com.programmers.springbootbasic.domain.user.domain.entity.User;

public class CsvUser {

    private String id;
    private String name;
    private String blocked;

    public CsvUser() {
    }

    public CsvUser(String id, String name, String blocked) {
        this.id = id;
        this.name = name;
        this.blocked = blocked;
    }

    public static CsvUser of(User user) {
        return new CsvUser(user.getId().toString(), user.getNickname(),
            String.valueOf(user.isBlocked()));
    }

    public User toEntity() {
        return new User(Long.valueOf(id), name, Boolean.getBoolean(blocked));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

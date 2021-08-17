package com.programmers.kdtspringorder.user;

public class User {
    private Long id;
    private String userId;
    private String name;

    public User(){}

    public User(Long id, String userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

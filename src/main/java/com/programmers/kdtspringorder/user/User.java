package com.programmers.kdtspringorder.user;

public class User {
    private String userId;
    private String name;

    public User( String userId, String name) {
        this.userId = userId;
        this.name = name;
    }


    @Override
    public String toString() {
        return "User{userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

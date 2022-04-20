package org.programmer.kdtspringboot.user;

import java.util.UUID;

public class BlackListUser implements User {

    private final UUID userId;
    private final String userName;

    public BlackListUser(UUID userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "BlackListUser{" +
                "userId=" + userId +
                ", userName='" + userName +
                '}';
    }

    @Override
    public String getInfo() {
        return userId + "," + userName;
    }
}

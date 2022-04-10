package org.programmers.kdtspring.entity.user;

import java.util.UUID;

public class BlackListedUser implements User {

    private final UUID userId;
    private final String userName;

    public BlackListedUser(UUID userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public UUID getUserId() {
        return this.userId;
    }
}

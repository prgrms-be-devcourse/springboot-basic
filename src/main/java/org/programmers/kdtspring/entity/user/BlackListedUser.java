package org.programmers.kdtspring.entity.user;

import java.util.UUID;

public class BlackListedUser implements User {

    private final UUID userId;
    private final Name name;

    public BlackListedUser(UUID userId, String name) {
        this.userId = userId;
        this.name = new Name(name);
    }

    @Override
    public Name getUserName() {
        return this.name;
    }

    @Override
    public UUID getUserId() {
        return this.userId;
    }
}
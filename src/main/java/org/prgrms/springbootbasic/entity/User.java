package org.prgrms.springbootbasic.entity;

import java.text.MessageFormat;

public class User {
    private final long userId;
    private final String name;

    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("'{' userId {0}, userName : {1}'}'", userId, name);
    }
}

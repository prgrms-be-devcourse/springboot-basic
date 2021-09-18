package org.prgrms.devcourse.blackuser;

import java.util.UUID;

public class BlackUser {
    private final UUID userId;
    private final String name;

    public BlackUser(UUID userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return userId + "," + name;
    }
}

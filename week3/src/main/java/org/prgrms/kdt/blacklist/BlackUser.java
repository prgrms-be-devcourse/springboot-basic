package org.prgrms.kdt.blacklist;

import java.util.UUID;

public class BlackUser {
    private final UUID uuid;
    private final String userName;

    public BlackUser(UUID uuid, String userName) {
        this.uuid = uuid;
        this.userName = userName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "BlackUser{" +
                "uuid=" + uuid +
                ", userName='" + userName + '\'' +
                '}';
    }
}

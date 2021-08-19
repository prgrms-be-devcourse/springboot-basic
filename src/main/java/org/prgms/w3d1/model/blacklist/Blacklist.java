package org.prgms.w3d1.model.blacklist;

import java.io.Serializable;
import java.util.UUID;

public class Blacklist implements Serializable {
    private final UUID blacklistId;
    private final String name;

    public Blacklist(UUID blacklistId, String name) {
        this.blacklistId = blacklistId;
        this.name = name;
    }

    public UUID getBlacklistId() {
        return blacklistId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "blacklistId=" + blacklistId +
                ", name='" + name + '\'' +
                '}';
    }
}

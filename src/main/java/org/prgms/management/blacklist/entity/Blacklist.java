package org.prgms.management.blacklist.entity;

import java.util.UUID;

public class Blacklist {
    private final UUID blacklistId;
    private final UUID userId;

    public Blacklist(UUID blacklistId, UUID userId) {
        this.blacklistId = blacklistId;
        this.userId = userId;
    }

    public UUID getBlacklistId() {
        return blacklistId;
    }

    public UUID getUserId() {
        return userId;
    }
}

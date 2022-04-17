package org.prgms.management.blacklist.entity;

import java.util.UUID;

public class Blacklist {
    private final UUID blacklistId;
    private final UUID customerId;

    public Blacklist(UUID blacklistId, UUID customerId) {
        this.blacklistId = blacklistId;
        this.customerId = customerId;
    }

    public UUID getBlacklistId() {
        return blacklistId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}

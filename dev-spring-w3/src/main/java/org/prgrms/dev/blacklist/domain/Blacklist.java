package org.prgrms.dev.blacklist.domain;

import java.util.UUID;

public class Blacklist {
    private final UUID customerId;
    private final String name;

    public Blacklist(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "customerId=" + customerId +
                ", name=" + name;
    }
}

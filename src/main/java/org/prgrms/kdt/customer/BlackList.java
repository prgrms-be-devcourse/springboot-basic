package org.prgrms.kdt.customer;

import java.util.UUID;

public class BlackList {
    private final UUID customerId;
    private final String name;
    private final boolean blacklist;

    public BlackList(final UUID customerId, final String name, final boolean blacklist) {
        this.customerId = customerId;
        this.name = name;
        this.blacklist = blacklist;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return name;
    }
}

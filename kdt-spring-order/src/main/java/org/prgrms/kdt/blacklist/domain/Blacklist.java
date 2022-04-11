package org.prgrms.kdt.blacklist.domain;

import java.util.UUID;

public class Blacklist {
    private final UUID customerId;
    private final String name;

    public Blacklist(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() { return "name : " + name; }

}

package org.prgrms.kdtspringdemo.customer;

import java.text.MessageFormat;
import java.util.UUID;

public class BlackCustomer implements Customer {
    private UUID uuid;
    private String name;
    public BlackCustomer(UUID customerId, String name) {
        this.uuid = customerId;
        this.name = name;
    }

    @Override
    public UUID getId() {
        return UUID.randomUUID();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("id : {0}, name : {1}", uuid, name);
    }
}

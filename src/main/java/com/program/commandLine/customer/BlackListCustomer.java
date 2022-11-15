package com.program.commandLine.customer;

import java.util.UUID;

public class BlackListCustomer implements Customer {
    private final UUID customerId;
    private final String name;

    public BlackListCustomer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String getName() {
        return name;
    }
}

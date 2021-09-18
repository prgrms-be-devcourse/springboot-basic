package org.programmers.customer.model;

import java.util.UUID;

public class BlackListCustomer {
    private final UUID blackListCustomerId;
    private final String name;


    public BlackListCustomer(UUID blackListCustomerId, String name) {
        this.blackListCustomerId = blackListCustomerId;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public UUID getCustomerId() {
        return blackListCustomerId;
    }

    @Override
    public String toString() {
        return "Customer ID : " + blackListCustomerId + " / " + "Name : " + name;
    }
}

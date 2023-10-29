package com.prgrms.springbasic.domain.customer.entity;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final String email;
    private boolean isBlackList;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public void toBlackList() {
        this.isBlackList = true;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBlackList() {
        return isBlackList;
    }
}

package org.prgrms.prgrmsspring.entity.user;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final Boolean isBlack;

    public Customer(UUID customerId, String name, Boolean isBlack) {
        this.customerId = customerId;
        this.name = name;
        this.isBlack = isBlack;
    }

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.isBlack = false;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsBlack() {
        return isBlack;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", isBlack=" + isBlack +
                '}';
    }
}

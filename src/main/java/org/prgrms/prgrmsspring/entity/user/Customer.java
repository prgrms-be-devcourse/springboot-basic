package org.prgrms.prgrmsspring.entity.user;

import java.util.UUID;


public class Customer {
    private final UUID customerId;
    private final String name;
    private final Boolean isBlack;
    private final String email;

    public Customer(UUID customerId, String name, Boolean isBlack, String email) {
        this.customerId = customerId;
        this.name = name;
        this.isBlack = isBlack;
        this.email = email;
    }

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", isBlack=" + isBlack +
                ", email='" + email + '\'' +
                '}';
    }
}

package org.prgms.kdtspringweek1.customer.entity;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private boolean isBlackCustomer = false;

    public Customer(UUID customerId, String name, boolean isBlackCustomer) {
        this.customerId = customerId;
        this.name = name;
        this.isBlackCustomer = isBlackCustomer;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", isBlackCustomer=" + isBlackCustomer +
                '}';
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public boolean getIsBlackCustomer() {
        return isBlackCustomer;
    }
}

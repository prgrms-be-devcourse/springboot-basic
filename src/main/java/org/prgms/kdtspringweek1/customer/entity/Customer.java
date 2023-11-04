package org.prgms.kdtspringweek1.customer.entity;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private boolean isBlackCustomer = false;

    public static Customer createWithName(String name) {
        return new Customer(name);
    }

    public static Customer createWithNameAndIsBlackCustomer(String name, boolean isBlackCustomer) {
        return new Customer(name, isBlackCustomer);
    }

    public static Customer createWithIdAndNameAndIsBlackCustomer(UUID customerId, String name, boolean isBlackCustomer) {
        return new Customer(customerId, name, isBlackCustomer);
    }

    private Customer(String name) {
        this.customerId = UUID.randomUUID();
        this.name = name;
    }

    private Customer(String name, boolean isBlackCustomer) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.isBlackCustomer = isBlackCustomer;
    }

    private Customer(UUID customerId, String name, boolean isBlackCustomer) {
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

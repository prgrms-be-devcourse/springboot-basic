package org.prgrms.kdt.customer.domain;

import java.util.UUID;

public class BannedCustomer extends Customer {
    private final String description;

    public BannedCustomer(UUID orderId, String email, String name, String description) {
        super(orderId, email, name);
        this.description = description;
    }

    @Override
    public String toString() {
        return "BannedCustomer{" +
                "orderId='" + getOrderId() + '\'' +
                "email='" + getEmail() + '\'' +
                "name='" + getName() + '\'' +
                "description='" + description + '\'' +
                '}';
    }
}

package org.prgrms.kdtspringvoucher.customer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final CustomerType type;
    private final UUID customerId;
    private String name;

    public Customer(CustomerType type, UUID customerId, String name) {
        this.type = type;
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                '}';
    }
}

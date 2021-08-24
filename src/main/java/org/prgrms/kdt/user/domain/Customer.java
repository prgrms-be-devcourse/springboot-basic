package org.prgrms.kdt.user.domain;

import java.util.Date;
import java.util.UUID;

public class Customer {
    private final UUID orderId;
    private final String email;
    private final String name;

    public Customer(UUID orderId, String email, String name) {
        this.orderId = orderId;
        this.email = email;
        this.name = name;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}

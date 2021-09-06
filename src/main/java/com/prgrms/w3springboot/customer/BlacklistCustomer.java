package com.prgrms.w3springboot.customer;

import java.util.UUID;

public class BlacklistCustomer {
    private final UUID customerId;
    private final String name;
    private final String reason;

    public BlacklistCustomer(UUID customerId, String name, String reason) {
        this.customerId = customerId;
        this.name = name;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BlacklistCustomer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}

package org.prgrms.vouchermanagement.customer.domain;

import java.util.UUID;

public class BlackCustomer implements Customer {

    private final UUID customerId;
    private final String name;

    public BlackCustomer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID : " + this.customerId + ", 이름 : " + this.name;
    }
}

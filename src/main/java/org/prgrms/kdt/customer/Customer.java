package org.prgrms.kdt.customer;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String customerName;
    private final boolean blacklist;

    public Customer(final UUID customerId, final String customerName, final boolean blacklist) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.blacklist = blacklist;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return customerName;
    }
}

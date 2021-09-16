package org.prgrms.kdtspringhomework.customer.domain;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String customerName;

    public Customer(final UUID customerId, final String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return customerId +
                "," + customerName;
    }
}

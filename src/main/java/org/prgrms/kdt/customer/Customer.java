package org.prgrms.kdt.customer;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String customerName;
    private boolean blacklist;

    public Customer(final UUID customerId, final String customerName, final boolean blacklist) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.blacklist = blacklist;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setBlacklist(final boolean blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public String toString() {
        return customerName;
    }
}

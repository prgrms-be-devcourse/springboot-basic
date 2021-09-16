package org.prgrms.kdtspringhomework.customer.domain;

import java.util.UUID;

public class BlackCustomer {
    private final UUID customerId;
    private final String customerName;

    public BlackCustomer(final UUID customerId, final String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }
}

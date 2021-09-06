package org.prgrms.kdtspringorder.customer.domain;

import java.util.UUID;

public class Customer {
    private final UUID id;

    public Customer(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}

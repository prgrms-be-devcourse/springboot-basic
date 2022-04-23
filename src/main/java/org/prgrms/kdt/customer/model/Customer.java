package org.prgrms.kdt.customer.model;

import java.util.UUID;

public class Customer {

    private final UUID uuid;
    private final String name;

    public Customer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "uuid=" + uuid +
            ", name='" + name + '\'' +
            '}';
    }

}

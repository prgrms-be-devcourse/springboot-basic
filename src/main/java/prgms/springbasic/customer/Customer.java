package prgms.springbasic.customer;

import java.util.UUID;

public class Customer {
    private final String name;
    private final UUID customerId;

    public Customer(String name) {
        this.name = name;
        this.customerId = UUID.randomUUID();
    }

    public Customer(String name, UUID customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return name + ", " + customerId;
    }
}

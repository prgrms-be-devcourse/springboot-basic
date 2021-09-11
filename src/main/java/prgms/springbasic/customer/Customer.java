package prgms.springbasic.customer;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, customerId);
    }
}

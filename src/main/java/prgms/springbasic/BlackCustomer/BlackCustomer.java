package prgms.springbasic.BlackCustomer;

import java.util.Objects;
import java.util.UUID;

public class BlackCustomer {
    private final String name;
    private final UUID customerId;

    public BlackCustomer(String name) {
        this.name = name;
        this.customerId = UUID.randomUUID();
    }

    public BlackCustomer(String name, UUID customerId) {
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
        BlackCustomer customer = (BlackCustomer) o;
        return Objects.equals(name, customer.name) && Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, customerId);
    }
}

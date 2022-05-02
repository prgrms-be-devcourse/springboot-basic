package org.prgrms.springbootbasic.entity.customer;

import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final Email email;
    private Name name;

    public Customer(UUID customerId, Name name, Email email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public void changeName(String newName) {
        name = new Name(newName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return getCustomerId().equals(customer.getCustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId());
    }
}

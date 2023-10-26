package org.prgrms.prgrmsspring.entity.user;

import java.util.Objects;
import java.util.UUID;


public class Customer {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final Boolean isBlack;

    public Customer(UUID customerId, String name, String email, Boolean isBlack) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.isBlack = isBlack;
    }

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.isBlack = false;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsBlack() {
        return isBlack;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Customer customer = (Customer) object;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(isBlack, customer.isBlack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, email, isBlack);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isBlack=" + isBlack +
                '}';
    }
}

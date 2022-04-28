package org.programmers.kdt.weekly.customer.model;

import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String email;
    private final String customerName;
    private CustomerType customerType;

    public Customer(UUID customerId, String email, String customerName, CustomerType customerType) {
        this.customerId = customerId;
        this.email = email;
        this.customerName = customerName;
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public Customer changeCustomerType(CustomerType customerType) {
        this.customerType = customerType;
        return this;
    }

    @Override
    public String toString() {
        return
            "customerId: " + customerId +
                ", customerName: " + customerName +
                ", customerEmail: " +email+
                ", customerType: " + customerType;
    }

    public String serializeCustomer() {
        return customerId + "," + email + "," + customerName + "," + customerType;
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
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
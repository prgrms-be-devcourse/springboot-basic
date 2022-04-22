package org.programmers.kdt.weekly.customer.model;

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

    public void changeCustomerType(CustomerType customerType) {
        this.customerType = customerType;
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
        return customerId + "," + email + "," + customerName + "," + customerType.toString();
    }
}
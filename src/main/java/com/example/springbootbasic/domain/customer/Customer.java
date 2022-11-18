package com.example.springbootbasic.domain.customer;

public class Customer {
    private final long customerId;
    private final CustomerStatus status;

    public Customer(long customerId, CustomerStatus status) {
        this.customerId = customerId;
        this.status = status;
    }

    public long getCustomerId() {
        return customerId;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public boolean isBlack() {
        return status.isBlack();
    }

    public boolean isNormal() { return status.isNormal(); }
}

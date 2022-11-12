package com.example.springbootbasic.domain.customer;

public class Customer {
    private final long customerId;
    private CustomerStatus status;

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
}

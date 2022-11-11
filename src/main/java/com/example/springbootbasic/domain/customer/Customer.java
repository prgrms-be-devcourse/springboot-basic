package com.example.springbootbasic.domain.customer;

import static com.example.springbootbasic.domain.customer.CustomerStatus.NORMAL;

public class Customer {
    private long customerId;
    private CustomerStatus status = NORMAL;

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

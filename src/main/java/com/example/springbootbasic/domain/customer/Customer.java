package com.example.springbootbasic.domain.customer;

import static com.example.springbootbasic.domain.customer.CustomerStatus.NORMAL;

public class Customer {
    private long id;
    private CustomerStatus status = NORMAL;

    public Customer(long id, CustomerStatus status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}

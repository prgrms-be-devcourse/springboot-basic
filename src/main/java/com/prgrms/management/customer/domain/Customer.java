package com.prgrms.management.customer.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private CustomerType customerType;

    public Customer(String name, String email, CustomerType customerType) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.customerType = customerType;
    }

    public Customer(CustomerType customerType) {
        this.customerId = UUID.randomUUID();
        this.customerType = customerType;
    }
}
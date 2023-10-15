package com.zerozae.voucher.domain.customer;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Customer {
    private final UUID customerId;
    private final String customerName;
    private final CustomerType customerType;

    public Customer(String customerName, CustomerType customerType) {
        this.customerId = UUID.randomUUID();
        this.customerName = customerName;
        this.customerType = customerType;
    }
    public Customer(UUID customerId, String customerName, CustomerType customerType) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = customerType;
    }
}

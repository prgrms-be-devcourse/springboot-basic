package com.example.voucher_manager.domain.customer;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Customer {
    private final UUID customerId;
    private final String customerName;

    public Customer(UUID customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "[CustomerId : " + customerId + ", customerName : " + customerName + "]";
    }
}

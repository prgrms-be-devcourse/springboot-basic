package com.programmers.voucher.domain.customer.domain;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }
}

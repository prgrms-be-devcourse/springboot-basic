package com.programmers.voucher.model.customer;

import lombok.Getter;

@Getter
public final class Customer {
    private final long customerId;
    private final String customerName;
    private final String email;

    public Customer(long customerId, String customerName, String email) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
    }
}

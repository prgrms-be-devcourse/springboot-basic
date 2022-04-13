package org.programmers.springbootbasic.customer.model;

import java.util.UUID;

public class BlackListCustomer implements Customer{
    private UUID customerId;
    private String customerName;

    public BlackListCustomer(UUID customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }
}

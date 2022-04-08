package org.programmers.kdt.weekly.customer;


import lombok.Getter;

import java.util.UUID;

@Getter
public class Customer {
    private UUID customerId;
    private String customerName;
    private CustomerType customerType;

    public Customer(UUID customerId, String customerName, CustomerType customerType) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = customerType;
    }

}

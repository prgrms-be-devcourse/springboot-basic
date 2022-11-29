package com.programmers.kwonjoosung.springbootbasicjoosung.controller.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;

public class CustomerDto {
    private final String customerId;
    private final String Name;

    public CustomerDto(String customerId, String customerName) {
        this.customerId = customerId;
        this.Name = customerName;
    }

    public CustomerDto(Customer customer) {
        this.customerId = customer.getCustomerId().toString();
        this.Name = customer.getName();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return Name;
    }
}

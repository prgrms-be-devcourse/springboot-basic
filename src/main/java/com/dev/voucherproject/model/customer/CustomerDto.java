package com.dev.voucherproject.model.customer;

import java.util.UUID;

public class CustomerDto {
    private UUID customerId;
    private String customerName;

    private CustomerDto(UUID customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public static CustomerDto fromCustomer(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getCustomerName());
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
}

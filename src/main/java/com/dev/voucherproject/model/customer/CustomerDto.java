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

    @Override
    public String toString() {
        return "[id, name] %s, %s".formatted(customerId.toString(), customerName);
    }
}

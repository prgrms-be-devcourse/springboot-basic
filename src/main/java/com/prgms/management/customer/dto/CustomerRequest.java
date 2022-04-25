package com.prgms.management.customer.dto;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;

public record CustomerRequest(
    String email,
    String type,
    String name
) {
    public Customer toCustomer() {
        return new Customer(name, CustomerType.valueOf(type.toUpperCase()), email);
    }
}

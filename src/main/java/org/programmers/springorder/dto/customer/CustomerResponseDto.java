package org.programmers.springorder.dto.customer;

import org.programmers.springorder.model.customer.Customer;

import java.util.UUID;

public class CustomerResponseDto {

    private final UUID customerId;
    private final String name;
    private final String customerType;

    public CustomerResponseDto(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.customerType = customer.getCustomerType().name();
    }

    public static CustomerResponseDto of(Customer customer) {
        return new CustomerResponseDto(customer);
    }

    @Override
    public String toString() {
        return "[" +
                "ID: " + customerId +
                ", NAME: " + name +
                ", TYPE: " + customerType +
                "]";

    }
}

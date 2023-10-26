package org.programmers.springorder.customer.model;

import org.programmers.springorder.customer.dto.CustomerRequestDto;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final CustomerType customerType;

    private Customer(UUID customerId, String name, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
    }

    public static Customer toCustomer(UUID customerId, String name, CustomerType customerType) {
        return new Customer(customerId, name, customerType);
    }

    private Customer(UUID customerId, CustomerRequestDto customerRequestDto) {
        this.customerId = customerId;
        this.name = customerRequestDto.getName();
        this.customerType = customerRequestDto.getCustomerType();
    }

    public static Customer of(UUID customerId, CustomerRequestDto customerRequestDto) {
        return new Customer(customerId, customerRequestDto);
    }

    public boolean isBlackList() {
        return this.customerType == CustomerType.BLACK;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

}

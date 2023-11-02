package org.programmers.springorder.model.customer;

import org.programmers.springorder.dto.customer.CustomerRequestDto;

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

    private static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static Customer of(CustomerRequestDto customerRequestDto) {
        UUID customerId = generateUUID();
        return new Customer(customerId, customerRequestDto);
    }

    public String getCustomerIdToString() {
        return this.customerId.toString();
    }

    public String getCustomerTypeName() {
        return this.customerType.name();
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

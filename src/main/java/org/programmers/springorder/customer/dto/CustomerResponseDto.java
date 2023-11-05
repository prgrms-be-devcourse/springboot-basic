package org.programmers.springorder.customer.dto;

import org.programmers.springorder.customer.model.Customer;

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

    public static CustomerResponseDto of(Customer customer){
        return new CustomerResponseDto(customer);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getCustomerType() {
        return customerType;
    }

    @Override
    public String toString() {
        return "customerId : " + customerId +'\n' +
                "name : " + name + '\n' +
                "customerType : " + customerType +'\n' +
                "==============================";

    }
}

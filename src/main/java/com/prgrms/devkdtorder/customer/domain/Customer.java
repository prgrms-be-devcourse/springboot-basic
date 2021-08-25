package com.prgrms.devkdtorder.customer.domain;

import java.util.UUID;

public class Customer {

    private final UUID customerId;

    private final String name;

    private final CustomerType customerType;

    public Customer(UUID customerId, String name, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
    }

    public Customer(String csvLine) {
        String[] split = csvLine.split(",");
        this.customerId = UUID.fromString(split[0]);
        this.name = split[1];
        this.customerType = CustomerType.valueOf(split[2]);
    }


    public CustomerType getCustomerType() {
        return customerType;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}

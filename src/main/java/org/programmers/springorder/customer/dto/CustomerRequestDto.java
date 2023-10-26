package org.programmers.springorder.customer.dto;

import org.programmers.springorder.customer.model.CustomerType;

public class CustomerRequestDto {
    private final String name;
    private final CustomerType customerType;

    public CustomerRequestDto(String name) {
        this.name = name;
        this.customerType = CustomerType.NORMAL;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }
}

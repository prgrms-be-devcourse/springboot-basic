package org.programmers.springorder.dto.customer;

import org.programmers.springorder.model.customer.CustomerType;

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

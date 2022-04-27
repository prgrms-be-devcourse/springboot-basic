package com.prgrms.management.customer.domain;

import lombok.Getter;

@Getter
public class CustomerRequest {
    private String name;
    private String email;
    private CustomerType customerType;

    public CustomerRequest(String name, String email, String customerType) {
        this.name = name;
        this.email = email;
        this.customerType = CustomerType.of(customerType);
    }
}

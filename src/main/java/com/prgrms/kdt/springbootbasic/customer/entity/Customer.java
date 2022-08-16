package com.prgrms.kdt.springbootbasic.customer.entity;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email; //email로 duplicate 체크함

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }
}

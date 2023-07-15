package com.prgrms.dto.customer;

import com.prgrms.model.customer.Customer;

import java.time.LocalDateTime;

public class CustomerResponse {

    private final String email;
    private final LocalDateTime createdAt;
    private final String name;
    private final LocalDateTime lastLoginAt;

    public CustomerResponse(Customer customer) {
        this.email = customer.getEmail();
        this.createdAt = customer.getCreatedAt();
        this.name = customer.getName();
        this.lastLoginAt = customer.getLastLoginAt();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ")
                .append(email).append(" ")
                .append(createdAt).append(" ")
                .append(lastLoginAt).append(" ");

        return sb.toString();
    }

    public String getName() {
        return name;
    }

}

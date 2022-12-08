package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.entity.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerInputDto {
    private final String name;
    private final String email;

    public CustomerInputDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer toCustomer() {
        return new Customer.Builder()
                .customerId(UUID.randomUUID())
                .name(name)
                .email(email)
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();
    }
}

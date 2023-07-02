package org.prgms.vouchermanagement.customer.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }
}

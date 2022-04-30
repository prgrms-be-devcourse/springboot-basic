package org.prgms.kdt.application.customer.domain;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void changeName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

}

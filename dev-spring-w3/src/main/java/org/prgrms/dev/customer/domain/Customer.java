package org.prgrms.dev.customer.domain;

import org.prgrms.dev.exception.InvalidNameException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new InvalidNameException("이름은 빈 값일 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return "customerId=" + customerId +
            ", name=" + name;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}


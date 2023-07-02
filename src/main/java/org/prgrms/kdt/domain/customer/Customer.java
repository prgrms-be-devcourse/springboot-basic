package org.prgrms.kdt.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;

    public Customer(UUID customerId, String email, String name) {
        this.customerId = customerId;
        this.email = email;
        this.name = name;

    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        validate(name);
        this.name = name;
    }

    public void validate(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("빈 이름입니다.");
        }
    }
}

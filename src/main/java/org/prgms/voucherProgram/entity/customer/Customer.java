package org.prgms.voucherProgram.entity.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime lastLoginTime;
    private final LocalDateTime createdTime;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginTime,
        LocalDateTime createdTime) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginTime = lastLoginTime;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "userId=%s, name=%s, email=%s, lastLoginAt =%s, createdAt =%s".formatted(customerId, name, email,
            lastLoginTime.toString(), createdTime);
    }
}

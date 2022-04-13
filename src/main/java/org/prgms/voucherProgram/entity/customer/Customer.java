package org.prgms.voucherProgram.entity.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final LocalDateTime createdTime;

    private Name name;
    private Email email;
    private LocalDateTime lastLoginTime;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdTime) {
        this(customerId, name, email, createdTime, null);
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdTime,
        LocalDateTime lastLoginTime) {
        this.customerId = customerId;
        this.name = new Name(name);
        this.email = new Email(email);
        this.createdTime = createdTime;
        this.lastLoginTime = lastLoginTime;
    }

    public void changeName(String name) {
        this.name = new Name(name);
    }

    public void changeEmail(String email) {
        this.email = new Email(email);
    }

    public void login() {
        this.lastLoginTime = LocalDateTime.now();
    }
}

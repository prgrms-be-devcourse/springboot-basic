package com.prgms.voucher.voucherproject.domain.customer;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;
    private final LocalDateTime createdAt;
    private String name;

    public Customer(String email, String name) {
        this.validateName(name);
        this.customerId = UUID.randomUUID();
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.name = name;
    }

    public Customer(UUID customerId, String email, LocalDateTime createdAt, String name) {
        this.validateName(name);
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
        this.name = name;
    }

    public void changeName(String name) {
        this.validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new InputMismatchException("이름을 입력해야 합니다.");
        }
    }

}

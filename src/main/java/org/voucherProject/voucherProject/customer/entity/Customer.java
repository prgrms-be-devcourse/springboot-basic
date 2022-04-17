package org.voucherProject.voucherProject.customer.entity;

import lombok.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@ToString
public class Customer {

    private final UUID customerId;

    private final String customerName;

    private final String customerEmail;

    private String password;

    private final LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    public Customer(UUID customerId, String customerName, String customerEmail) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.createdAt = LocalDateTime.now();
    }

    public Customer(UUID customerId, String customerName, String customerEmail, String password) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public Customer(UUID customerId, String customerName, String customerEmail, String password, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.password = password;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}

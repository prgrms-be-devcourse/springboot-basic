package org.voucherProject.voucherProject.customer.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@ToString
@Builder
@AllArgsConstructor
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

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}

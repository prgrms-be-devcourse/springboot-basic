package org.voucherProject.voucherProject.entity.customer;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    private final UUID customerId;

    private final String customerName;

    private final String customerEmail;

    private String password;

    private List<UUID> vouchers = new ArrayList<>();

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
}

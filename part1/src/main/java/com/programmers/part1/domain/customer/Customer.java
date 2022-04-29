package com.programmers.part1.domain.customer;

import com.programmers.part1.domain.voucher.Voucher;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Customer {

    private final UUID customerId;
    private String name;
    private String email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    @Builder
    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    @Override
    public String toString() {
        return String.format("id: %-20s name: %-10s email: %-30s", this.customerId, this.name, this.email);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }
}

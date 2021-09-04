package com.example.kdtspringmission.customer.domain;

import com.example.kdtspringmission.voucher.domain.Voucher;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    private final List<Voucher> wallet = new ArrayList<>();

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt,
        LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void login() {
        lastLoginAt = LocalDateTime.now();
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name Should Not Be Blank");
        }
    }

    public Voucher addVoucher(Voucher voucher) {
        voucher.setOwnerId(customerId);
        wallet.add(voucher);
        return voucher;
    }

    public List<Voucher> getWallet() {
        return wallet;
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return customerId.equals(customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}

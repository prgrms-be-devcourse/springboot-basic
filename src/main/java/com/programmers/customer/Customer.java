package com.programmers.customer;

import com.programmers.voucher.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;
    private List<Voucher> wallet;

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt) {
        validateName(name);
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createAt = createAt;
        this.wallet = new ArrayList<>();
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createAt = createAt;
        this.wallet = new ArrayList<>();
    }

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getCustomerId(), customer.getCustomerId()) && Objects.equals(getName(), customer.getName()) && Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getName(), getEmail());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void addVoucher(Voucher voucher) {
        this.wallet.add(voucher);
    }

    public void removeVoucher(Voucher voucher) {
        if (!wallet.remove(voucher)) {
            throw new RuntimeException("바우처 삭제 실패");
        }
    }

    public List<Voucher> getWallet() {
        return wallet;
    }

    public void makeWallet(List<Voucher> wallet) {
        this.wallet = wallet;
    }
}
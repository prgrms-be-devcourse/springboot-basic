package com.programmers.kdtspringorder.customer;

import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.domain.VoucherWallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;
    private final VoucherWallet voucherWallet;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this(customerId, name, email, null, createdAt);
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        voucherWallet = new VoucherWallet();
    }

    public Voucher addVoucher(Voucher voucher) {
        voucher.allocateVoucherToCustomer(customerId);
        return voucherWallet.add(voucher);
    }

    public Voucher removeVoucher(UUID voucherId) {
        return voucherWallet.remove(voucherId);
    }

    public List<Voucher> findAllVoucher() {
        return voucherWallet.findAll();
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
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
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

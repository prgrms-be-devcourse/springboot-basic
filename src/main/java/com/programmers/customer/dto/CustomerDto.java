package com.programmers.customer.dto;

import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerDto {
    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;
    private List<VoucherDto> wallet;

    public CustomerDto() {
    }

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime createAt) {
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createAt = createAt;
        this.wallet = new ArrayList<>();
    }

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime createAt, List<VoucherDto> wallet) {
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createAt = createAt;
        this.wallet = wallet;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public List<VoucherDto> getWallet() {
        return wallet;
    }

    public void setWallet(List<VoucherDto> wallet) {
        this.wallet = wallet;
    }
}

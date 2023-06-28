package com.example.voucher.domain.dto;

import java.util.UUID;

public class VoucherDto {
    private UUID voucherId;
    private double amount;

    public VoucherDto(UUID voucherId, double amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

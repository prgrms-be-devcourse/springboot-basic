package com.example.voucher.domain.dto;

import com.example.voucher.domain.Voucher;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final double amount;

    public VoucherDto(UUID voucherId, double amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public double getAmount() {
        return amount;
    }
}

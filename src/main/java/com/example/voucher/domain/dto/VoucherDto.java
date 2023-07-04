package com.example.voucher.domain.dto;

import com.example.voucher.domain.Voucher;
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

    public double getAmount() {
        return amount;
    }

    public VoucherDto toDto(Voucher voucher) {
        return new VoucherDto(voucherId, amount);
    }

    public Voucher fromDto(VoucherDto voucherDto) {
        return new Voucher(voucherId, amount);
    }
}

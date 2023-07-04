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

    public static VoucherDto fromVoucher(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getAmount());
    }

    public Voucher toVoucher() {
        return new Voucher(voucherId, amount);
    }
}

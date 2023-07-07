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

    public Voucher toVoucher() {
        return new Voucher(voucherId, amount);
    }
}

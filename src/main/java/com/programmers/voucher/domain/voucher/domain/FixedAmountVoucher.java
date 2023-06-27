package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId);
        this.amount = amount;
    }

    @Override
    public VoucherDto toDto() {
        return new VoucherDto(super.voucherId, VoucherType.FIXED_AMOUNT, amount);
    }

    @Override
    public long totalAmount(long beforeAmount) {
        return beforeAmount - amount;
    }

    @Override
    public String fullInfoString() {
        return "VoucherID: " + voucherId + ", discount: " + amount + "$";
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}

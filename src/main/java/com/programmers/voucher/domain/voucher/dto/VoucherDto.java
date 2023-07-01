package com.programmers.voucher.domain.voucher.dto;

import com.programmers.voucher.domain.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long amount;

    public VoucherDto(UUID voucherId, VoucherType voucherType, long amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}

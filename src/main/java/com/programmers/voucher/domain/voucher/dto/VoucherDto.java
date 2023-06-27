package com.programmers.voucher.domain.voucher.dto;

import com.programmers.voucher.domain.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherDto {
    private final UUID customerId;
    private final VoucherType voucherType;
    private final long amount;

    public VoucherDto(UUID customerId, VoucherType voucherType, long amount) {
        this.customerId = customerId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}

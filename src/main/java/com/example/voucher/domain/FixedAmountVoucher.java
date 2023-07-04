package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.constant.VoucherType;
import com.example.voucher.utils.validator.VoucherValidator;

public class FixedAmountVoucher implements Voucher {

    VoucherType voucherType = VoucherType.FixedAmountDiscount;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getValue() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeAmount) {
        VoucherValidator.validateNonZero(beforeAmount);
        VoucherValidator.validatePositive(beforeAmount);
        VoucherValidator.validateGreaterThan(beforeAmount, amount);

        return beforeAmount - amount;
    }

}

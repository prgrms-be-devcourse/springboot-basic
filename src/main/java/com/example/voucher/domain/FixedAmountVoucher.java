package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.constant.VoucherType;
import com.example.voucher.utils.Validator;

public class FixedAmountVoucher implements Voucher {

    VoucherType voucherType = VoucherType.FIXED_AMOUNT_DISCOUNT;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.voucherId = UUID.randomUUID();
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
        Validator.validateNonZero(beforeAmount);
        Validator.validatePositive(beforeAmount);
        Validator.validateGreaterThan(beforeAmount, amount);

        return beforeAmount - amount;
    }

}
